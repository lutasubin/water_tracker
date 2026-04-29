package com.weappsinc.watertracker.app.feature.weight.domain.repository

import kotlinx.coroutines.flow.Flow

interface WeightRepository {
    fun observeWeight(): Flow<Int>
    suspend fun saveWeight(weight: Int)
}
