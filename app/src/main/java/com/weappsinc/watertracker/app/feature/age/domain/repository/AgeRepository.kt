package com.weappsinc.watertracker.app.feature.age.domain.repository

import kotlinx.coroutines.flow.Flow

interface AgeRepository {
    fun observeAge(): Flow<Int>
    suspend fun saveAge(age: Int)
}
