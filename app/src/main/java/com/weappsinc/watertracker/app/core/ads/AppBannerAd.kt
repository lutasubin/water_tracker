package com.weappsinc.watertracker.app.core.ads

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.LoadAdError
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
        var loadState by remember(unitId, adWidth) { mutableStateOf(AdUiLoadState.Loading) }
        val adView = remember(unitId, adWidth) {
            AdView(context).apply {
                this.adUnitId = unitId
                setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth))
                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        loadState = AdUiLoadState.Loaded
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        loadState = AdUiLoadState.Failed
                    }
                }
            }
        }
        LaunchedEffect(adView) {
            loadState = AdUiLoadState.Loading
            adView.loadAd(AdRequest.Builder().build())
        }
        LaunchedEffect(loadState, adView) {
            if (loadState != AdUiLoadState.Failed) return@LaunchedEffect
            delay(ADS_RETRY_DELAY_MS)
            if (loadState == AdUiLoadState.Failed) {
                loadState = AdUiLoadState.Loading
                adView.loadAd(AdRequest.Builder().build())
            }
        }
        DisposableEffect(adView) {
            onDispose { adView.destroy() }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            if (loadState != AdUiLoadState.Loaded) {
                BannerAdLoadingPlaceholder()
            }
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { adView },
            )
        }
    }
}
