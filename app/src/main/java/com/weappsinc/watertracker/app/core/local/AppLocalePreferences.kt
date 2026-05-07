package com.weappsinc.watertracker.app.core.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/** DataStore lưu BCP 47 language tag (vd. en-US, vi). */
private val Context.appLocaleDataStore by preferencesDataStore(name = "app_locale")

object AppLocalePreferences {
    private val localeTagKey = stringPreferencesKey("locale_tag")
    private val localeOnboardingDoneKey = booleanPreferencesKey("locale_onboarding_completed")

    /** Mặc định cài đặt lần đầu: English (US). */
    const val DEFAULT_LOCALE_TAG = "en-US"

    /**
     * Ghi mặc định vào DataStore nếu chưa có — tránh lần đầu mở app bám theo locale máy (vd. vi).
     */
    suspend fun seedDefaultLocaleIfAbsent(context: Context) {
        val prefs = context.appLocaleDataStore.data.first()
        if (prefs[localeTagKey] == null) {
            context.appLocaleDataStore.edit { it[localeTagKey] = DEFAULT_LOCALE_TAG }
        }
    }

    fun observeTag(context: Context) =
        context.appLocaleDataStore.data.map { prefs ->
            prefs[localeTagKey] ?: DEFAULT_LOCALE_TAG
        }

    suspend fun readTag(context: Context): String =
        observeTag(context).first()

    suspend fun saveTag(context: Context, tag: String) {
        context.appLocaleDataStore.edit { it[localeTagKey] = tag }
    }

    /** Luồng lần đầu: đã xác nhận màn chọn ngôn ngữ (✓) hay chưa. */
    fun observeLocaleOnboardingCompleted(context: Context) =
        context.appLocaleDataStore.data.map { prefs ->
            prefs[localeOnboardingDoneKey] == true
        }

    suspend fun markLocaleOnboardingCompleted(context: Context) {
        context.appLocaleDataStore.edit { it[localeOnboardingDoneKey] = true }
    }

    /** Đọc một lần: đã ✓ màn onboarding ngôn ngữ chưa. */
    suspend fun readLocaleOnboardingCompleted(context: Context): Boolean =
        observeLocaleOnboardingCompleted(context).first()
}
