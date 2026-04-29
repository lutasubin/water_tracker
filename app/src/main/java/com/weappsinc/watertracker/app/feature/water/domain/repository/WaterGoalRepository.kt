package com.weappsinc.watertracker.app.feature.water.domain.repository

import kotlinx.coroutines.flow.Flow

interface WaterGoalRepository {
    fun observeDailyGoalMl(): Flow<Int>
}

