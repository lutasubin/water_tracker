package com.weappsinc.watertracker.app.core.ads

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd

/** Cache NativeAd theo placement; sau khi hiển thị thì destroy và preload slot kế. */
class NativeAdCache {
    private data class Slot(val ad: NativeAd, val unitId: String)

    private val slots = mutableMapOf<NativePlacement, Slot>()
    private val loading = mutableSetOf<NativePlacement>()

    fun preload(context: Context, placement: NativePlacement, unitId: String) {
        if (unitId.isBlank()) return
        val existing = slots[placement]
        if (existing != null && existing.unitId == unitId) return
        if (loading.contains(placement)) return
        destroy(placement)
        loading.add(placement)
        AdLoader.Builder(context.applicationContext, unitId)
            .forNativeAd { loaded ->
                loading.remove(placement)
                slots[placement]?.ad?.destroy()
                slots[placement] = Slot(loaded, unitId)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    loading.remove(placement)
                }
            })
            .build()
            .loadAd(AdRequest.Builder().build())
    }

    fun takeReady(placement: NativePlacement, unitId: String): NativeAd? {
        val slot = slots.remove(placement) ?: return null
        if (slot.unitId != unitId) {
            slot.ad.destroy()
            return null
        }
        return slot.ad
    }

    fun isLoading(placement: NativePlacement): Boolean = loading.contains(placement)

    fun hasReady(placement: NativePlacement, unitId: String): Boolean {
        val slot = slots[placement] ?: return false
        return slot.unitId == unitId
    }

    fun destroy(placement: NativePlacement) {
        loading.remove(placement)
        slots.remove(placement)?.ad?.destroy()
    }

    fun destroyAll() {
        NativePlacement.entries.forEach { destroy(it) }
    }
}
