package com.weappsinc.watertracker.app.core.config

import com.weappsinc.watertracker.app.core.ads.AdsTestIds
import com.weappsinc.watertracker.app.core.ads.BannerPlacement
import com.weappsinc.watertracker.app.core.ads.NativePlacement

data class AdsConfig(
    val showAds: Boolean,
    val useTestAds: Boolean,
    val bannerDefaultId: String,
    val bannerHomeId: String,
    val interstitialId: String,
    val nativeDefaultId: String,
    val nativeHomeId: String,
    val nativeLanguageId: String,
    val nativeOnboardingId: String,
    val appOpenId: String,
    val rewardedId: String,
) {
    fun bannerUnitId(placement: BannerPlacement): String = when {
        useTestAds -> AdsTestIds.BANNER
        placement == BannerPlacement.Default -> bannerDefaultId
        else -> bannerHomeId
    }

    fun nativeUnitId(placement: NativePlacement): String = when {
        useTestAds -> AdsTestIds.NATIVE
        placement == NativePlacement.Default -> nativeDefaultId
        placement == NativePlacement.Home -> nativeHomeId
        placement == NativePlacement.Language -> nativeLanguageId
        else -> nativeOnboardingId
    }

    fun interstitialUnitId(): String = if (useTestAds) AdsTestIds.INTERSTITIAL else interstitialId

    fun appOpenUnitId(): String = if (useTestAds) AdsTestIds.APP_OPEN else appOpenId

    fun rewardedUnitId(): String = if (useTestAds) AdsTestIds.REWARDED else rewardedId

    companion object {
        fun defaults(): AdsConfig = AdsConfig(
            showAds = false,
            useTestAds = true,
            bannerDefaultId = AdsTestIds.BANNER,
            bannerHomeId = AdsTestIds.BANNER,
            interstitialId = AdsTestIds.INTERSTITIAL,
            nativeDefaultId = AdsTestIds.NATIVE,
            nativeHomeId = AdsTestIds.NATIVE,
            nativeLanguageId = AdsTestIds.NATIVE,
            nativeOnboardingId = AdsTestIds.NATIVE,
            appOpenId = AdsTestIds.APP_OPEN,
            rewardedId = AdsTestIds.REWARDED,
        )
    }
}
