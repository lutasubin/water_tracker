package com.weappsinc.watertracker.app.core.ads

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

/** Cache AdView theo placement để banner hiện nhanh khi vào màn. */
class BannerAdCache {
    private data class Slot(
        val adView: AdView,
        val unitId: String,
        val widthDp: Int,
        var loaded: Boolean = false,
    )

    private val slots = mutableMapOf<BannerPlacement, Slot>()
    private val loading = mutableSetOf<BannerPlacement>()

    fun preload(context: Context, placement: BannerPlacement, unitId: String, widthDp: Int) {
        if (unitId.isBlank() || widthDp < 1) return
        val existing = slots[placement]
        if (existing != null && existing.unitId == unitId && existing.widthDp == widthDp) {
            if (existing.loaded || loading.contains(placement)) return
        } else {
            destroy(placement)
        }
        if (loading.contains(placement)) return
        loading.add(placement)
        val appContext = context.applicationContext
        val adView = AdView(appContext).apply {
            adUnitId = unitId
            setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(appContext, widthDp))
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    loading.remove(placement)
                    slots[placement]?.loaded = true
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    loading.remove(placement)
                    destroy(placement)
                }
            }
        }
        slots[placement] = Slot(adView, unitId, widthDp)
        adView.loadAd(AdRequest.Builder().build())
    }

    fun readyAdView(placement: BannerPlacement, unitId: String, widthDp: Int): AdView? {
        val slot = slots[placement] ?: return null
        if (slot.unitId != unitId || slot.widthDp != widthDp || !slot.loaded) return null
        return slot.adView
    }

    fun isLoading(placement: BannerPlacement): Boolean = loading.contains(placement)

    fun destroy(placement: BannerPlacement) {
        loading.remove(placement)
        slots.remove(placement)?.adView?.destroy()
    }

    fun destroyAll() {
        BannerPlacement.entries.forEach { destroy(it) }
    }
}
