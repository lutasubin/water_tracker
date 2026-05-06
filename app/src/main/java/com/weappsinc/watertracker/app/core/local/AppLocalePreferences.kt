package com.weappsinc.watertracker.app.core.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/** DataStore lưu BCP 47 language tag (vd. en-US, vi). */
private val Context.appLocaleDataStore by preferencesDataStore(name = "app_locale")

object AppLocalePreferences {
    private val localeTagKey = stringPreferencesKey("locale_tag")

    /** Mặc định trùng mockup: English (US). */
    const val DEFAULT_LOCALE_TAG = "en-US"

    fun observeTag(context: Context) =
        context.appLocaleDataStore.data.map { prefs ->
            prefs[localeTagKey] ?: DEFAULT_LOCALE_TAG
        }

    suspend fun readTag(context: Context): String =
        observeTag(context).first()

    suspend fun saveTag(context: Context, tag: String) {
        context.appLocaleDataStore.edit { it[localeTagKey] = tag }
    }
}
