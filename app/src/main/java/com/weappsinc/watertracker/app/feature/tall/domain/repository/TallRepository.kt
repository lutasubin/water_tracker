package com.weappsinc.watertracker.app.feature.tall.domain.repository

import kotlinx.coroutines.flow.Flow

interface TallRepository {
    fun observeTall(): Flow<Int>
    suspend fun saveTall(tall: Int)
}
