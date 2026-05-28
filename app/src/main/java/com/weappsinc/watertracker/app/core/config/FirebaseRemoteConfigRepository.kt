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

    private val mutableAdsConfig = MutableStateFlow(AdsRemoteConfigDefaults.offlineConfig())

    override val adsConfig: StateFlow<AdsConfig> = mutableAdsConfig

    init {
        remoteConfig.setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
        )
        remoteConfig.setDefaultsAsync(AdsRemoteConfigDefaults.inAppDefaultMap())
    }

    override suspend fun refresh(): Result<AdsConfig> = runCatching {
        fetchAndActivate()
        readConfig().also { mutableAdsConfig.value = it }
    }

    private fun readConfig(): AdsConfig = AdsConfig(
        showAds = remoteConfig.getBoolean(RemoteConfigKeys.SHOW_ADS),
        useTestAds = remoteConfig.getBoolean(RemoteConfigKeys.USE_TEST_ADS),
        bannerDefaultId = remoteString(RemoteConfigKeys.BANNER_AD),
        bannerHomeId = remoteString(RemoteConfigKeys.BANNER_HOME),
        interstitialId = remoteString(RemoteConfigKeys.INTERSTITIAL_AD),
        nativeDefaultId = remoteString(RemoteConfigKeys.NATIVE_AD),
        nativeHomeId = remoteString(RemoteConfigKeys.NATIVE_HOME),
        nativeLanguageId = remoteString(RemoteConfigKeys.NATIVE_LANGUAGE),
        nativeOnboardingId = remoteString(RemoteConfigKeys.NATIVE_ONBOARDING),
        appOpenId = remoteString(RemoteConfigKeys.APP_OPEN_AD),
        rewardedId = remoteString(RemoteConfigKeys.REWARDED_AD),
    )

    private fun remoteString(key: String): String = remoteConfig.getString(key).trim()

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
