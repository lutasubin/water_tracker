package com.weappsinc.watertracker.app.feature.gender.data.repository

import com.weappsinc.watertracker.app.core.local.GenderSQLiteHelper
import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import com.weappsinc.watertracker.app.feature.gender.domain.repository.GenderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GenderRepositoryImpl(
    private val helper: GenderSQLiteHelper
) : GenderRepository {
    private val selectedGender = MutableStateFlow(GenderType.fromValue(helper.getGender()))

    override fun observeSelectedGender(): Flow<GenderType> {
        return selectedGender.asStateFlow()
    }

    override suspend fun saveSelectedGender(gender: GenderType) {
        helper.saveGender(gender.name)
        selectedGender.value = gender
    }
}
