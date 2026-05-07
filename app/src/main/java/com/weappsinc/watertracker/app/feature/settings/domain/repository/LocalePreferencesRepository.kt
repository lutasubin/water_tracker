package com.weappsinc.watertracker.app.feature.settings.domain.repository

import kotlinx.coroutines.flow.Flow

/** Trạng thái locale + bước chọn ngôn ngữ lần đầu. */
interface LocalePreferencesRepository {
    fun observeLocaleOnboardingCompleted(): Flow<Boolean>
    suspend fun markLocaleOnboardingCompleted()
}
