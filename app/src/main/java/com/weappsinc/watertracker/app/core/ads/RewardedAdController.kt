package com.weappsinc.watertracker.app.core.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class RewardedAdController {
    private var cachedAd: RewardedAd? = null
    private var cachedUnitId: String? = null
    private var isLoading = false

    fun preload(context: Context, adUnitId: String) {
        if (cachedUnitId != adUnitId) {
            cachedAd = null
            cachedUnitId = adUnitId
        }
        if (isLoading || cachedAd != null) return
        isLoading = true
        RewardedAd.load(
            context,
            adUnitId,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    isLoading = false
                    cachedAd = rewardedAd
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoading = false
                    cachedAd = null
                }
            }
        )
    }

    fun show(
        activity: Activity,
        adUnitId: String,
        onRewardEarned: () -> Unit,
        onDismiss: () -> Unit,
    ) {
        val ad = cachedAd ?: run {
            preload(activity.applicationContext, adUnitId)
            onDismiss()
            return
        }
        cachedAd = null
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                preload(activity.applicationContext, adUnitId)
                onDismiss()
            }

            override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                preload(activity.applicationContext, adUnitId)
                onDismiss()
            }
        }
        ad.show(activity) { onRewardEarned() }
    }
}
