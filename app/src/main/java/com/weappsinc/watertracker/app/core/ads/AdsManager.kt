package com.weappsinc.watertracker.app.core.ads

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.nativead.NativeAd
import com.weappsinc.watertracker.app.core.config.AdsConfig
import kotlinx.coroutines.flow.StateFlow

interface AdsManager {
    val state: StateFlow<AdsRuntimeState>

    fun initialize(context: Context)

    suspend fun refreshConfig(): Result<AdsConfig>

    fun warmUp(context: Context)

    fun preloadAllKnownPlacements(context: Context, bannerWidthDp: Int = 360)

    fun preloadBanner(context: Context, placement: BannerPlacement, widthDp: Int)

    fun preloadNative(context: Context, placement: NativePlacement)

    fun readyBanner(placement: BannerPlacement, unitId: String, widthDp: Int): AdView?

    fun takeNative(placement: NativePlacement, unitId: String): NativeAd?

    fun onNativeDisplayed(context: Context, placement: NativePlacement, unitId: String)

    fun preloadAppOpen(context: Context)

    suspend fun awaitAppOpenReady(timeoutMs: Long): Boolean

    fun preloadInterstitial(context: Context)

    suspend fun awaitInterstitialReady(timeoutMs: Long): Boolean

    fun preloadRewarded(context: Context)

    fun showAppOpen(activity: Activity, onDismiss: () -> Unit = {})

    suspend fun showInterstitialWhenReady(
        activity: Activity,
        timeoutMs: Long = INTERSTITIAL_READY_TIMEOUT_MS,
        onDismiss: () -> Unit,
    )

    fun showRewarded(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onDismiss: () -> Unit,
    )
}

const val INTERSTITIAL_READY_TIMEOUT_MS = 800L

val LocalAdsManager = staticCompositionLocalOf<AdsManager> {
    error("AdsManager chua duoc provide.")
}
