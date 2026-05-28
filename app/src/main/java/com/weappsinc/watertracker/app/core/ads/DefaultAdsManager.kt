package com.weappsinc.watertracker.app.core.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdView
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
    private val bannerCache = BannerAdCache()
    private val nativeCache = NativeAdCache()
    private val mutableState = MutableStateFlow(AdsRuntimeState(config = remoteConfigRepository.currentConfig()))
    private var appContext: Context? = null
    private var initializeRequested = false
    private var sdkReady = false

    override val state: StateFlow<AdsRuntimeState> = mutableState.asStateFlow()

    override fun initialize(context: Context) {
        appContext = context.applicationContext
        syncState()
        if (initializeRequested) return
        initializeRequested = true
        MobileAds.initialize(appContext!!) {
            sdkReady = true
            mutableState.value = mutableState.value.copy(isInitialized = true)
            onSdkAndConfigReady()
        }
    }

    override suspend fun refreshConfig(): Result<AdsConfig> {
        val result = remoteConfigRepository.refresh()
        syncState(result.getOrElse { remoteConfigRepository.currentConfig() })
        appContext?.let { ctx ->
            if (!initializeRequested) initialize(ctx)
            else if (sdkReady) onSdkAndConfigReady()
        }
        return result
    }

    override fun warmUp(context: Context) {
        initialize(context)
        appContext?.let { onSdkAndConfigReady() }
    }

    override fun preloadAllKnownPlacements(context: Context, bannerWidthDp: Int) {
        if (!canRequestAds()) return
        preloadBanner(context, BannerPlacement.Home, bannerWidthDp)
        preloadBanner(context, BannerPlacement.Default, bannerWidthDp)
        preloadNative(context, NativePlacement.Onboarding)
        preloadNative(context, NativePlacement.Language)
        preloadNative(context, NativePlacement.Home)
        preloadNative(context, NativePlacement.Default)
        preloadInterstitial(context)
        preloadAppOpen(context)
        preloadRewarded(context)
    }

    override fun preloadBanner(context: Context, placement: BannerPlacement, widthDp: Int) {
        val unitId = state.value.config.bannerUnitId(placement).takeIf { canRequestAds() } ?: return
        bannerCache.preload(context, placement, unitId, widthDp)
    }

    override fun preloadNative(context: Context, placement: NativePlacement) {
        val unitId = state.value.config.nativeUnitId(placement).takeIf { canRequestAds() } ?: return
        nativeCache.preload(context, placement, unitId)
    }

    override fun readyBanner(placement: BannerPlacement, unitId: String, widthDp: Int): AdView? =
        bannerCache.readyAdView(placement, unitId, widthDp)

    override fun takeNative(placement: NativePlacement, unitId: String) =
        nativeCache.takeReady(placement, unitId)

    override fun onNativeDisplayed(context: Context, placement: NativePlacement, unitId: String) {
        preloadNative(context, placement)
    }

    override fun preloadAppOpen(context: Context) {
        val unitId = state.value.config.appOpenUnitId().takeIf { canUseFullScreenAds() } ?: return
        appOpenController.preload(context.applicationContext, unitId)
    }

    override suspend fun awaitAppOpenReady(timeoutMs: Long): Boolean {
        val context = appContext ?: return false
        if (!canRequestAds()) return false
        val startMs = System.currentTimeMillis()
        while (System.currentTimeMillis() - startMs < timeoutMs) {
            initialize(context)
            preloadAppOpen(context)
            if (appOpenController.isAdAvailable()) return true
            delay(150)
        }
        return appOpenController.isAdAvailable()
    }

    override fun preloadInterstitial(context: Context) {
        val unitId = state.value.config.interstitialUnitId().takeIf { canUseFullScreenAds() } ?: return
        interstitialController.preload(context.applicationContext, unitId)
    }

    override suspend fun awaitInterstitialReady(timeoutMs: Long): Boolean {
        val context = appContext ?: return false
        if (!canUseFullScreenAds()) return false
        val startMs = System.currentTimeMillis()
        while (System.currentTimeMillis() - startMs < timeoutMs) {
            preloadInterstitial(context)
            if (interstitialController.isAdAvailable()) return true
            delay(120)
        }
        return interstitialController.isAdAvailable()
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

    override suspend fun showInterstitialWhenReady(
        activity: Activity,
        timeoutMs: Long,
        onDismiss: () -> Unit,
    ) {
        if (!canUseFullScreenAds()) {
            onDismiss()
            return
        }
        awaitInterstitialReady(timeoutMs)
        val unitId = state.value.config.interstitialUnitId()
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

    private fun onSdkAndConfigReady() {
        val ctx = appContext ?: return
        if (!sdkReady || !canRequestAds()) return
        preloadAllKnownPlacements(ctx)
    }

    private fun canRequestAds(): Boolean = state.value.canRequestAds()

    private fun canUseFullScreenAds(): Boolean = canRequestAds() && sdkReady

    private fun syncState(config: AdsConfig = remoteConfigRepository.currentConfig()) {
        mutableState.value = mutableState.value.copy(
            isEligible = AdsEligibilityProvider.isAdsSupported(),
            config = config,
        )
    }
}
