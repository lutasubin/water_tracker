package com.weappsinc.watertracker.app.feature.weigh.domain.repository

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoal
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoalLog
import kotlinx.coroutines.flow.Flow

interface WeighCompletedGoalRepository {
    fun observeAllCompletedDesc(): Flow<List<WeighCompletedGoal>>
    fun observeCompletedById(goalId: Long): Flow<WeighCompletedGoal?>
    fun observeCompletedLogsByGoalId(goalId: Long): Flow<List<WeighCompletedGoalLog>>
    suspend fun insertCompleted(goal: WeighCompletedGoal): Result<Long>
    suspend fun insertCompletedWithLogs(
        goal: WeighCompletedGoal,
        logs: List<WeighCompletedGoalLog>,
    ): Result<Long>
}
