package com.weappsinc.watertracker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.weappsinc.watertracker.app.core.local.AppLocalePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Cold start: áp locale từ DataStore; lần cài mới [AppLocalePreferences.seedDefaultLocaleIfAbsent] seed tag khớp ngôn ngữ máy.
 * Chỉ dùng runBlocking một lần ở Application; đọc DataStore trên [Dispatchers.IO]
 * để không chạy chuỗi suspend trên event loop của main (tránh rủi ro so với runBlocking mặc định trong Activity).
 */
class DrinkWaterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val tag = runBlocking(Dispatchers.IO) {
            AppLocalePreferences.seedDefaultLocaleIfAbsent(applicationContext)
            AppLocalePreferences.readTag(applicationContext)
        }
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
    }
}
