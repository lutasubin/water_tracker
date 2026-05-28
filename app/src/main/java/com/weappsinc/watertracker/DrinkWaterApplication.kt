package com.weappsinc.watertracker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.google.android.gms.ads.MobileAds
import com.weappsinc.watertracker.app.core.ads.AdsManager
import com.weappsinc.watertracker.app.core.ads.DefaultAdsManager
import com.weappsinc.watertracker.app.core.config.FirebaseRemoteConfigRepository
import com.weappsinc.watertracker.app.core.config.RemoteConfigRepository
import com.weappsinc.watertracker.app.core.local.AppLocalePreferences
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

/**
 * Cold start: áp locale từ DataStore; fetch Remote Config song song Mobile Ads init.
 */
class DrinkWaterApplication : Application() {
    val remoteConfigRepository: RemoteConfigRepository by lazy {
        FirebaseRemoteConfigRepository()
    }

    val adsManager: AdsManager by lazy {
        DefaultAdsManager(remoteConfigRepository)
    }

    override fun onCreate() {
        super.onCreate()
        val tag = runBlocking(Dispatchers.IO) {
            AppLocalePreferences.seedDefaultLocaleIfAbsent(applicationContext)
            val localeTag = AppLocalePreferences.readTag(applicationContext)
            coroutineScope {
                val configJob = async { remoteConfigRepository.refresh() }
                val sdkJob = async {
                    suspendCoroutine { cont ->
                        MobileAds.initialize(applicationContext) { cont.resume(Unit) }
                    }
                }
                configJob.await()
                sdkJob.await()
            }
            adsManager.warmUp(applicationContext)
            localeTag
        }
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
    }
}
