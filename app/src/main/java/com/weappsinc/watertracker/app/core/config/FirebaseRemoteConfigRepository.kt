package com.weappsinc.watertracker.app.core.config

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseRemoteConfigRepository(
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig,
) : RemoteConfigRepository {

    private val defaults = AdsConfig.defaults()
    private val mutableAdsConfig = MutableStateFlow(defaults)

    override val adsConfig: StateFlow<AdsConfig> = mutableAdsConfig

    init {
        remoteConfig.setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
        )
        remoteConfig.setDefaultsAsync(
            mapOf(
                RemoteConfigKeys.SHOW_ADS to defaults.showAds,
                RemoteConfigKeys.USE_TEST_ADS to defaults.useTestAds,
                RemoteConfigKeys.BANNER_AD to defaults.bannerDefaultId,
                RemoteConfigKeys.BANNER_HOME to defaults.bannerHomeId,
                RemoteConfigKeys.INTERSTITIAL_AD to defaults.interstitialId,
                RemoteConfigKeys.NATIVE_AD to defaults.nativeDefaultId,
                RemoteConfigKeys.NATIVE_HOME to defaults.nativeHomeId,
                RemoteConfigKeys.NATIVE_LANGUAGE to defaults.nativeLanguageId,
                RemoteConfigKeys.NATIVE_ONBOARDING to defaults.nativeOnboardingId,
                RemoteConfigKeys.APP_OPEN_AD to defaults.appOpenId,
                RemoteConfigKeys.REWARDED_AD to defaults.rewardedId,
            )
        )
    }

    override suspend fun refresh(): Result<AdsConfig> = runCatching {
        fetchAndActivate()
        readConfig().also { mutableAdsConfig.value = it }
    }

    private fun readConfig(): AdsConfig = AdsConfig(
        showAds = remoteConfig.getBoolean(RemoteConfigKeys.SHOW_ADS),
        useTestAds = remoteConfig.getBoolean(RemoteConfigKeys.USE_TEST_ADS),
        bannerDefaultId = remoteString(RemoteConfigKeys.BANNER_AD, defaults.bannerDefaultId),
        bannerHomeId = remoteString(RemoteConfigKeys.BANNER_HOME, defaults.bannerHomeId),
        interstitialId = remoteString(RemoteConfigKeys.INTERSTITIAL_AD, defaults.interstitialId),
        nativeDefaultId = remoteString(RemoteConfigKeys.NATIVE_AD, defaults.nativeDefaultId),
        nativeHomeId = remoteString(RemoteConfigKeys.NATIVE_HOME, defaults.nativeHomeId),
        nativeLanguageId = remoteString(RemoteConfigKeys.NATIVE_LANGUAGE, defaults.nativeLanguageId),
        nativeOnboardingId = remoteString(RemoteConfigKeys.NATIVE_ONBOARDING, defaults.nativeOnboardingId),
        appOpenId = remoteString(RemoteConfigKeys.APP_OPEN_AD, defaults.appOpenId),
        rewardedId = remoteString(RemoteConfigKeys.REWARDED_AD, defaults.rewardedId),
    )

    private fun remoteString(key: String, fallback: String): String {
        return remoteConfig.getString(key).ifBlank { fallback }
    }

    private suspend fun fetchAndActivate() {
        suspendCoroutine { continuation ->
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Unit)
                } else {
                    continuation.resumeWithException(
                        task.exception ?: IllegalStateException("Khong the fetch Remote Config.")
                    )
                }
            }
        }
    }
}
