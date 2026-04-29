package com.weappsinc.watertracker.app.feature.weight.data.repository

import com.weappsinc.watertracker.app.core.local.GenderSQLiteHelper
import com.weappsinc.watertracker.app.feature.weight.domain.repository.WeightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeightRepositoryImpl(private val helper: GenderSQLiteHelper) : WeightRepository {
    private val selectedWeight = MutableStateFlow(helper.getWeight() ?: 65)

    override fun observeWeight(): Flow<Int> = selectedWeight.asStateFlow()

    override suspend fun saveWeight(weight: Int) {
        helper.saveWeight(weight)
        selectedWeight.value = weight
    }
}
