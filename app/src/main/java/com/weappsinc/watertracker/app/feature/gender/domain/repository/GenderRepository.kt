package com.weappsinc.watertracker.app.feature.gender.domain.repository

import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import kotlinx.coroutines.flow.Flow

interface GenderRepository {
    fun observeSelectedGender(): Flow<GenderType>
    suspend fun saveSelectedGender(gender: GenderType)
}
