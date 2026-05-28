package com.weappsinc.watertracker.app.core.ads

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.delay

@Composable
fun AppBannerAd(
    modifier: Modifier = Modifier,
    placement: BannerPlacement = BannerPlacement.Default,
    adsManager: AdsManager = LocalAdsManager.current,
) {
    val runtimeState by adsManager.state.collectAsState()
    if (!runtimeState.canRequestAds()) return
    val unitId = runtimeState.config.bannerUnitId(placement).ifBlank { return }
    val context = LocalContext.current
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val adWidth = maxWidth.value.toInt().coerceAtLeast(1)
        var adView by remember(unitId, adWidth) { mutableStateOf<AdView?>(null) }
        var loadState by remember(unitId, adWidth) { mutableStateOf(AdUiLoadState.Loading) }
        var retryAttempt by remember(unitId, adWidth) { mutableIntStateOf(0) }
        LaunchedEffect(unitId, adWidth, retryAttempt) {
            loadState = AdUiLoadState.Loading
            adView = adsManager.readyBanner(placement, unitId, adWidth)
            if (adView != null) {
                loadState = AdUiLoadState.Loaded
                return@LaunchedEffect
            }
            adsManager.preloadBanner(context, placement, adWidth)
            repeat(24) {
                delay(150)
                val ready = adsManager.readyBanner(placement, unitId, adWidth)
                if (ready != null) {
                    adView = ready
                    loadState = AdUiLoadState.Loaded
                    return@LaunchedEffect
                }
            }
            loadState = AdUiLoadState.Failed
            delay(AdsRetryPolicy.delayForAttempt(retryAttempt))
            retryAttempt++
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            if (loadState != AdUiLoadState.Loaded || adView == null) {
                BannerAdLoadingPlaceholder()
            }
            adView?.let { view ->
                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { view },
                )
            }
        }
    }
}
