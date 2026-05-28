package com.weappsinc.watertracker.app.core.ads

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.coroutines.delay

@Composable
fun AppNativeAd(
    modifier: Modifier = Modifier,
    placement: NativePlacement = NativePlacement.Default,
    adsManager: AdsManager = LocalAdsManager.current,
) {
    val runtimeState by adsManager.state.collectAsState()
    if (!runtimeState.canRequestAds()) return
    val unitId = runtimeState.config.nativeUnitId(placement).ifBlank { return }
    val context = LocalContext.current
    var nativeAd by remember(unitId) { mutableStateOf<NativeAd?>(null) }
    var loadState by remember(unitId) { mutableStateOf(AdUiLoadState.Loading) }
    var retryAttempt by remember(unitId) { mutableIntStateOf(0) }
    LaunchedEffect(unitId, retryAttempt) {
        val cached = adsManager.takeNative(placement, unitId)
        if (cached != null) {
            nativeAd = cached
            loadState = AdUiLoadState.Loaded
            return@LaunchedEffect
        }
        loadState = AdUiLoadState.Loading
        adsManager.preloadNative(context, placement)
        repeat(24) {
            delay(150)
            val ready = adsManager.takeNative(placement, unitId)
            if (ready != null) {
                nativeAd = ready
                loadState = AdUiLoadState.Loaded
                return@LaunchedEffect
            }
        }
        loadState = AdUiLoadState.Failed
    }
    LaunchedEffect(loadState, retryAttempt) {
        if (loadState != AdUiLoadState.Failed) return@LaunchedEffect
        delay(AdsRetryPolicy.delayForAttempt(retryAttempt))
        retryAttempt++
    }
    DisposableEffect(placement, unitId) {
        onDispose {
            nativeAd?.destroy()
            nativeAd = null
            adsManager.onNativeDisplayed(context, placement, unitId)
        }
    }
    val loadedAd = nativeAd
    Box(modifier = modifier.fillMaxWidth()) {
        if (loadState != AdUiLoadState.Loaded) {
            NativeAdLoadingPlaceholder()
        }
        loadedAd?.let { ad ->
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { createNativeAdView(it) },
                update = { bindNativeAdView(it, ad) },
            )
        }
    }
}
