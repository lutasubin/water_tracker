package com.weappsinc.watertracker.app.core.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.weappsinc.watertracker.app.core.config.AdsConfig
import com.weappsinc.watertracker.app.core.config.RemoteConfigRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultAdsManager(
    private val remoteConfigRepository: RemoteConfigRepository,
) : AdsManager {

    private val interstitialController = InterstitialAdController()
    private val rewardedController = RewardedAdController()
    private val appOpenController = AppOpenAdController()
    private val mutableState = MutableStateFlow(AdsRuntimeState(config = remoteConfigRepository.currentConfig()))
    private var appContext: Context? = null
    private var initializeRequested = false

    override val state: StateFlow<AdsRuntimeState> = mutableState.asStateFlow()

    override fun initialize(context: Context) {
        appContext = context.applicationContext
        syncState()
        if (!state.value.canRequestAds() || initializeRequested) return
        initializeRequested = true
        MobileAds.initialize(appContext!!) {
            mutableState.value = mutableState.value.copy(isInitialized = true)
            appContext?.let { readyContext ->
                preloadAppOpen(readyContext)
                preloadInterstitial(readyContext)
                preloadRewarded(readyContext)
            }
        }
    }

    override suspend fun refreshConfig(): Result<AdsConfig> {
        val result = remoteConfigRepository.refresh()
        syncState(result.getOrElse { remoteConfigRepository.currentConfig() })
        appContext?.let { initialize(it) }
        return result
    }

    override fun warmUp(context: Context) {
        initialize(context)
        preloadAppOpen(context)
        preloadInterstitial(context)
        preloadRewarded(context)
    }

    override fun preloadAppOpen(context: Context) {
        val unitId = state.value.config.appOpenUnitId().takeIf { canUseFullScreenAds() } ?: return
        appOpenController.preload(context.applicationContext, unitId)
    }

    override suspend fun awaitAppOpenReady(timeoutMs: Long): Boolean {
        val context = appContext ?: return false
        if (!state.value.canRequestAds()) return false
        val startMs = System.currentTimeMillis()
        while (System.currentTimeMillis() - startMs < timeoutMs) {
            initialize(context)
            preloadAppOpen(context)
            if (appOpenController.isAdAvailable()) return true
            delay(250)
        }
        return appOpenController.isAdAvailable()
    }

    override fun preloadInterstitial(context: Context) {
        val unitId = state.value.config.interstitialUnitId().takeIf { canUseFullScreenAds() } ?: return
        interstitialController.preload(context.applicationContext, unitId)
    }

    override fun preloadRewarded(context: Context) {
        val unitId = state.value.config.rewardedUnitId().takeIf { canUseFullScreenAds() } ?: return
        rewardedController.preload(context.applicationContext, unitId)
    }

    override fun showAppOpen(activity: Activity, onDismiss: () -> Unit) {
        val unitId = state.value.config.appOpenUnitId().takeIf { canUseFullScreenAds() } ?: run {
            onDismiss()
            return
        }
        appOpenController.show(activity, unitId, onDismiss)
    }

    override fun showInterstitial(activity: Activity, onDismiss: () -> Unit) {
        val unitId = state.value.config.interstitialUnitId().takeIf { canUseFullScreenAds() } ?: run {
            onDismiss()
            return
        }
        interstitialController.show(activity, unitId, onDismiss)
    }

    override fun showRewarded(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onDismiss: () -> Unit,
    ) {
        val unitId = state.value.config.rewardedUnitId().takeIf { canUseFullScreenAds() } ?: run {
            onDismiss()
            return
        }
        rewardedController.show(activity, unitId, onRewardEarned, onDismiss)
    }

    private fun canUseFullScreenAds(): Boolean {
        return state.value.canRequestAds() && state.value.isInitialized
    }

    private fun syncState(config: AdsConfig = remoteConfigRepository.currentConfig()) {
        mutableState.value = mutableState.value.copy(
            isEligible = AdsEligibilityProvider.isAdsSupported(),
            config = config,
        )
    }
}
