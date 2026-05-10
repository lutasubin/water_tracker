package com.weappsinc.watertracker.app.feature.weigh.data.repository

import com.weappsinc.watertracker.app.feature.weigh.data.local.WeighCompletedGoalDao
import com.weappsinc.watertracker.app.feature.weigh.data.local.WeighCompletedGoalEntity
import com.weappsinc.watertracker.app.feature.weigh.data.local.WeighCompletedGoalLogEntity
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoal
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoalLog
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighCompletedGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeighCompletedGoalRepositoryImpl(
    private val dao: WeighCompletedGoalDao
) : WeighCompletedGoalRepository {

    override fun observeAllCompletedDesc(): Flow<List<WeighCompletedGoal>> =
        dao.observeAllDesc().map { list -> list.map { it.toDomain() } }

    override fun observeCompletedById(goalId: Long): Flow<WeighCompletedGoal?> =
        dao.observeById(goalId).map { it?.toDomain() }

    override fun observeCompletedLogsByGoalId(goalId: Long): Flow<List<WeighCompletedGoalLog>> =
        dao.observeLogsByGoalId(goalId).map { list -> list.map { it.toDomain() } }

    override suspend fun insertCompleted(goal: WeighCompletedGoal): Result<Long> = runCatching {
        dao.insert(
            WeighCompletedGoalEntity(
                id = 0,
                targetWeightKg = goal.targetWeightKg,
                journeyStartWeightKg = goal.journeyStartWeightKg,
                achievedBodyWeightKg = goal.achievedBodyWeightKg,
                completedAtEpochDay = goal.completedAtEpochDay,
                completedAtMs = goal.completedAtMs,
            )
        )
    }

    override suspend fun insertCompletedWithLogs(
        goal: WeighCompletedGoal,
        logs: List<WeighCompletedGoalLog>,
    ): Result<Long> = runCatching {
        dao.insertGoalWithLogs(
            goal = WeighCompletedGoalEntity(
                id = 0,
                targetWeightKg = goal.targetWeightKg,
                journeyStartWeightKg = goal.journeyStartWeightKg,
                achievedBodyWeightKg = goal.achievedBodyWeightKg,
                completedAtEpochDay = goal.completedAtEpochDay,
                completedAtMs = goal.completedAtMs,
            ),
            logs = logs.map {
                WeighCompletedGoalLogEntity(
                    id = 0,
                    goalId = 0,
                    epochDay = it.epochDay,
                    weightKg = it.weightKg,
                    recordedAtMs = it.recordedAtMs,
                )
            },
        )
    }

    private fun WeighCompletedGoalEntity.toDomain() = WeighCompletedGoal(
        id = id,
        targetWeightKg = targetWeightKg,
        journeyStartWeightKg = journeyStartWeightKg,
        achievedBodyWeightKg = achievedBodyWeightKg,
        completedAtEpochDay = completedAtEpochDay,
        completedAtMs = completedAtMs,
    )

    private fun WeighCompletedGoalLogEntity.toDomain() = WeighCompletedGoalLog(
        epochDay = epochDay,
        weightKg = weightKg,
        recordedAtMs = recordedAtMs,
    )
}
