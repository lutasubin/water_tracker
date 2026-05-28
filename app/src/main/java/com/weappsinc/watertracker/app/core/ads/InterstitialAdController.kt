package com.weappsinc.watertracker.app.core.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialAdController {
    private var cachedAd: InterstitialAd? = null
    private var cachedUnitId: String? = null
    private var isLoading = false

    fun isAdAvailable(): Boolean = cachedAd != null

    fun preload(context: Context, adUnitId: String) {
        if (cachedUnitId != adUnitId) {
            cachedAd = null
            cachedUnitId = adUnitId
        }
        if (isLoading || cachedAd != null) return
        isLoading = true
        InterstitialAd.load(
            context,
            adUnitId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    isLoading = false
                    cachedAd = interstitialAd
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoading = false
                    cachedAd = null
                }
            }
        )
    }

    fun show(activity: Activity, adUnitId: String, onDismiss: () -> Unit) {
        val ad = cachedAd ?: run {
            preload(activity.applicationContext, adUnitId)
            onDismiss()
            return
        }
        cachedAd = null
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                onDismiss()
                preload(activity.applicationContext, adUnitId)
            }

            override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                onDismiss()
                preload(activity.applicationContext, adUnitId)
            }
        }
        ad.show(activity)
    }
}
