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
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
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
    var retryToken by remember(unitId) { mutableIntStateOf(0) }
    DisposableEffect(context, unitId, retryToken) {
        var disposed = false
        loadState = AdUiLoadState.Loading
        val adLoader = AdLoader.Builder(context, unitId)
            .forNativeAd { loadedAd ->
                if (disposed) {
                    loadedAd.destroy()
                    return@forNativeAd
                }
                nativeAd?.destroy()
                nativeAd = loadedAd
                loadState = AdUiLoadState.Loaded
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    nativeAd?.destroy()
                    nativeAd = null
                    loadState = AdUiLoadState.Failed
                }
            })
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
        onDispose {
            disposed = true
            nativeAd?.destroy()
            nativeAd = null
        }
    }
    LaunchedEffect(loadState, unitId) {
        if (loadState != AdUiLoadState.Failed) return@LaunchedEffect
        delay(ADS_RETRY_DELAY_MS)
        if (loadState == AdUiLoadState.Failed) {
            retryToken++
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
