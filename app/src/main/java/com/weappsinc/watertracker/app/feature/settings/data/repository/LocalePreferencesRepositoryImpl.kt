package com.weappsinc.watertracker.app.feature.settings.data.repository

import android.content.Context
import com.weappsinc.watertracker.app.core.local.AppLocalePreferences
import com.weappsinc.watertracker.app.feature.settings.domain.repository.LocalePreferencesRepository
import kotlinx.coroutines.flow.Flow

class LocalePreferencesRepositoryImpl(
    private val appContext: Context,
) : LocalePreferencesRepository {
    override fun observeLocaleOnboardingCompleted(): Flow<Boolean> =
        AppLocalePreferences.observeLocaleOnboardingCompleted(appContext)

    override suspend fun markLocaleOnboardingCompleted() {
        AppLocalePreferences.markLocaleOnboardingCompleted(appContext)
    }
}
