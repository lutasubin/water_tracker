package com.weappsinc.watertracker.app.core.config

/** Fallback offline trước fetch; unit ID thật lấy từ Firebase Remote Config sau activate. */
object AdsRemoteConfigDefaults {
    fun offlineConfig(): AdsConfig = AdsConfig(
        showAds = false,
        useTestAds = false,
        bannerDefaultId = "",
        bannerHomeId = "",
        interstitialId = "",
        nativeDefaultId = "",
        nativeHomeId = "",
        nativeLanguageId = "",
        nativeOnboardingId = "",
        appOpenId = "",
        rewardedId = "",
    )

    fun inAppDefaultMap(): Map<String, Any> = mapOf(
        RemoteConfigKeys.SHOW_ADS to false,
        RemoteConfigKeys.USE_TEST_ADS to false,
        RemoteConfigKeys.BANNER_AD to "",
        RemoteConfigKeys.BANNER_HOME to "",
        RemoteConfigKeys.INTERSTITIAL_AD to "",
        RemoteConfigKeys.NATIVE_AD to "",
        RemoteConfigKeys.NATIVE_HOME to "",
        RemoteConfigKeys.NATIVE_LANGUAGE to "",
        RemoteConfigKeys.NATIVE_ONBOARDING to "",
        RemoteConfigKeys.APP_OPEN_AD to "",
        RemoteConfigKeys.REWARDED_AD to "",
    )
}
