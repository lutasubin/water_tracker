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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Cold start: chỉ block ngắn cho locale; ads + Remote Config chạy nền tránh ANR.
 */
class DrinkWaterApplication : Application() {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val remoteConfigRepository: RemoteConfigRepository by lazy {
        FirebaseRemoteConfigRepository()
    }

    val adsManager: AdsManager by lazy {
        DefaultAdsManager(remoteConfigRepository)
    }

    override fun onCreate() {
        super.onCreate()
        val localeTag = runBlocking(Dispatchers.IO) {
            AppLocalePreferences.seedDefaultLocaleIfAbsent(applicationContext)
            AppLocalePreferences.readTag(applicationContext)
        }
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(localeTag))
        startAdsBootstrapAsync()
    }

    private fun startAdsBootstrapAsync() {
        appScope.launch(Dispatchers.IO) {
            runCatching { remoteConfigRepository.refresh() }
            suspendCoroutine { cont ->
                MobileAds.initialize(applicationContext) { cont.resume(Unit) }
            }
            adsManager.warmUp(applicationContext)
        }
    }
}
