package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.ArchiveCompletedWeightGoalOutcome
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoal
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoalLog
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeightGoalCompletionSnapshot
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighCompletedGoalRepository
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository

/** Ghi hành trình hoàn thành vào Room rồi xóa mục tiêu/hành trình trong DataStore. */
class ArchiveCompletedWeightGoalUseCase(
    private val completedGoalRepository: WeighCompletedGoalRepository,
    private val weighLogRepository: WeighLogRepository,
    private val saveTargetWeightKg: SaveWeighTargetWeightKgUseCase,
    private val saveJourneyStartWeightKg: SaveWeighJourneyStartWeightKgUseCase,
) {
    suspend operator fun invoke(
        snapshot: WeightGoalCompletionSnapshot,
    ): Result<ArchiveCompletedWeightGoalOutcome> {
        val row = WeighCompletedGoal(
            id = 0L,
            targetWeightKg = snapshot.targetWeightKg.toDouble(),
            journeyStartWeightKg = snapshot.journeyStartWeightKg.toDouble(),
            achievedBodyWeightKg = snapshot.achievedBodyWeightKg.toDouble(),
            completedAtEpochDay = snapshot.completedAtEpochDay,
            completedAtMs = snapshot.completedAtMs,
        )
        val logsResult = weighLogRepository.listLogsBetweenEpochDays(
            startEpochDay = snapshot.completedAtEpochDay - 3650L,
            endEpochDay = snapshot.completedAtEpochDay,
        )
        val fallbackLog = WeighCompletedGoalLog(
            epochDay = snapshot.completedAtEpochDay,
            weightKg = snapshot.achievedBodyWeightKg.toDouble(),
            recordedAtMs = snapshot.completedAtMs,
        )
        val logs = logsResult.getOrDefault(emptyList())
            .map {
                WeighCompletedGoalLog(
                    epochDay = it.epochDay,
                    weightKg = it.weightKg,
                    recordedAtMs = it.recordedAtMs,
                )
            }
            .ifEmpty { listOf(fallbackLog) }
        val goalId = completedGoalRepository.insertCompletedWithLogs(row, logs)
            .getOrElse { return Result.failure(it) }
        val prefsCleared = runCatching {
            saveTargetWeightKg(null)
            saveJourneyStartWeightKg(null)
            true
        }.getOrDefault(false)
        return Result.success(
            ArchiveCompletedWeightGoalOutcome(
                archivedGoalId = goalId,
                preferencesCleared = prefsCleared,
            ),
        )
    }
}
