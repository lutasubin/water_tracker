package com.weappsinc.watertracker.app.core.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

class AppOpenAdController {
    private var cachedAd: AppOpenAd? = null
    private var cachedUnitId: String? = null
    private var isLoading = false
    private var isShowing = false

    fun isAdAvailable(): Boolean = cachedAd != null

    fun preload(context: Context, adUnitId: String) {
        if (cachedUnitId != adUnitId) {
            cachedAd = null
            cachedUnitId = adUnitId
        }
        if (isLoading || cachedAd != null || isShowing) return
        isLoading = true
        AppOpenAd.load(
            context,
            adUnitId,
            AdRequest.Builder().build(),
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(appOpenAd: AppOpenAd) {
                    isLoading = false
                    cachedAd = appOpenAd
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoading = false
                    cachedAd = null
                }
            }
        )
    }

    fun show(activity: Activity, adUnitId: String, onDismiss: () -> Unit) {
        if (isShowing || activity.isFinishing) {
            onDismiss()
            return
        }
        val ad = cachedAd ?: run {
            preload(activity.applicationContext, adUnitId)
            onDismiss()
            return
        }
        cachedAd = null
        isShowing = true
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                finishShow(activity, adUnitId, onDismiss)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                finishShow(activity, adUnitId, onDismiss)
            }
        }
        ad.show(activity)
    }

    private fun finishShow(activity: Activity, adUnitId: String, onDismiss: () -> Unit) {
        isShowing = false
        onDismiss()
        preload(activity.applicationContext, adUnitId)
    }
}
