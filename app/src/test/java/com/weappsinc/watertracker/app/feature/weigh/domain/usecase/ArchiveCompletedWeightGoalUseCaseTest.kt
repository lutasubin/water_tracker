package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoal
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoalLog
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeightGoalCompletionSnapshot
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighCompletedGoalRepository
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ArchiveCompletedWeightGoalUseCaseTest {

    @Test
    fun insertFails_prefsNotCleared() = runBlocking {
        val completed = FailingCompletedRepo()
        val prefs = RecordingPrefsRepo()
        val uc = ArchiveCompletedWeightGoalUseCase(
            completedGoalRepository = completed,
            weighLogRepository = FakeLogRepo(),
            weighPreferencesRepository = prefs,
            saveTargetWeightKg = SaveWeighTargetWeightKgUseCase(prefs),
            saveJourneyStartWeightKg = SaveWeighJourneyStartWeightKgUseCase(prefs),
        )
        val snap = snap()
        assertTrue(uc(snap).isFailure)
        assertEquals(0f, prefs.savedTarget) // không gọi save null
        assertEquals(0f, prefs.savedJourney)
    }

    @Test
    fun success_insertsThenClearsPrefs() = runBlocking {
        val completed = OkCompletedRepo()
        val prefs = RecordingPrefsRepo()
        val uc = ArchiveCompletedWeightGoalUseCase(
            completedGoalRepository = completed,
            weighLogRepository = FakeLogRepo(),
            weighPreferencesRepository = prefs,
            saveTargetWeightKg = SaveWeighTargetWeightKgUseCase(prefs),
            saveJourneyStartWeightKg = SaveWeighJourneyStartWeightKgUseCase(prefs),
        )
        assertTrue(uc(snap()).isSuccess)
        assertEquals(1, completed.insertCount)
        assertNull(prefs.savedTarget)
        assertNull(prefs.savedJourney)
    }

    @Test
    fun clearPrefsFails_stillReturnsSuccessWithFlagFalse() = runBlocking {
        val completed = OkCompletedRepo()
        val uc = ArchiveCompletedWeightGoalUseCase(
            completedGoalRepository = completed,
            weighLogRepository = FakeLogRepo(),
            weighPreferencesRepository = FailingPrefsRepo(),
            saveTargetWeightKg = SaveWeighTargetWeightKgUseCase(FailingPrefsRepo()),
            saveJourneyStartWeightKg = SaveWeighJourneyStartWeightKgUseCase(FailingPrefsRepo()),
        )
        val outcome = uc(snap()).getOrThrow()
        assertEquals(1, completed.insertCount)
        assertTrue(!outcome.preferencesCleared)
    }

    private fun snap() = WeightGoalCompletionSnapshot(
        targetWeightKg = 55f,
        journeyStartWeightKg = 60f,
        achievedBodyWeightKg = 55f,
        completedAtEpochDay = 20_000L,
        completedAtMs = 1_700_000_000_000L,
    )

    private class FailingCompletedRepo : WeighCompletedGoalRepository {
        override fun observeAllCompletedDesc(): Flow<List<WeighCompletedGoal>> = flowOf(emptyList())
        override fun observeCompletedById(goalId: Long): Flow<WeighCompletedGoal?> = flowOf(null)
        override fun observeCompletedLogsByGoalId(goalId: Long): Flow<List<WeighCompletedGoalLog>> =
            flowOf(emptyList())

        override suspend fun insertCompleted(goal: WeighCompletedGoal): Result<Long> =
            Result.failure(RuntimeException("db"))

        override suspend fun insertCompletedWithLogs(
            goal: WeighCompletedGoal,
            logs: List<WeighCompletedGoalLog>,
        ): Result<Long> = Result.failure(RuntimeException("db"))
    }

    private class OkCompletedRepo : WeighCompletedGoalRepository {
        var insertCount = 0
        override fun observeAllCompletedDesc(): Flow<List<WeighCompletedGoal>> = flowOf(emptyList())
        override fun observeCompletedById(goalId: Long): Flow<WeighCompletedGoal?> = flowOf(null)
        override fun observeCompletedLogsByGoalId(goalId: Long): Flow<List<WeighCompletedGoalLog>> =
            flowOf(emptyList())

        override suspend fun insertCompleted(goal: WeighCompletedGoal): Result<Long> {
            insertCount++
            return Result.success(1L)
        }

        override suspend fun insertCompletedWithLogs(
            goal: WeighCompletedGoal,
            logs: List<WeighCompletedGoalLog>,
        ): Result<Long> {
            insertCount++
            return Result.success(1L)
        }
    }

    private class FakeLogRepo : WeighLogRepository {
        override fun observeLatestTwoDesc(): Flow<List<WeighLogEntry>> = flowOf(emptyList())
        override fun observeLatestLog(): Flow<WeighLogEntry?> = flowOf(null)
        override fun observeLatestForEpochDay(epochDay: Long): Flow<WeighLogEntry?> = flowOf(null)
        override fun observeLogsBetweenEpochDays(
            startEpochDay: Long,
            endEpochDay: Long,
        ): Flow<List<WeighLogEntry>> = flowOf(emptyList())

        override suspend fun listLogsBetweenEpochDays(
            startEpochDay: Long,
            endEpochDay: Long,
        ): Result<List<WeighLogEntry>> = Result.success(emptyList())

        override suspend fun insertLog(epochDay: Long, weightKg: Double, recordedAtMs: Long): Result<Unit> =
            Result.success(Unit)
    }

    private class RecordingPrefsRepo : WeighPreferencesRepository {
        var savedTarget: Float? = 0f
        var savedJourney: Float? = 0f

        override fun observeMassUnit(): Flow<MassUnit> = flowOf(MassUnit.KG)
        override suspend fun saveMassUnit(unit: MassUnit) {}
        override fun observeTargetWeightKg(): Flow<Float?> = flowOf(55f)
        override suspend fun saveTargetWeightKg(weightKg: Float?) {
            savedTarget = weightKg
        }

        override fun observeJourneyStartWeightKg(): Flow<Float?> = flowOf(60f)
        override suspend fun saveJourneyStartWeightKg(weightKg: Float?) {
            savedJourney = weightKg
        }

        override fun observeWeightGoalMetDialogShownEpochDay(): Flow<Long?> = flowOf(null)
        override suspend fun saveWeightGoalMetDialogShownEpochDay(epochDay: Long) {}
        override fun observeWeightGoalMetDialogShownTargetKg(): Flow<Float?> = flowOf(null)
        override suspend fun saveWeightGoalMetDialogShownTargetKg(targetKg: Float?) {}
        override suspend fun clearWeightGoalMetDialogShownMarker() {}
    }

    private class FailingPrefsRepo : WeighPreferencesRepository {
        override fun observeMassUnit(): Flow<MassUnit> = flowOf(MassUnit.KG)
        override suspend fun saveMassUnit(unit: MassUnit) {}
        override fun observeTargetWeightKg(): Flow<Float?> = flowOf(55f)
        override suspend fun saveTargetWeightKg(weightKg: Float?) {
            error("prefs error")
        }

        override fun observeJourneyStartWeightKg(): Flow<Float?> = flowOf(60f)
        override suspend fun saveJourneyStartWeightKg(weightKg: Float?) {
            error("prefs error")
        }

        override fun observeWeightGoalMetDialogShownEpochDay(): Flow<Long?> = flowOf(null)
        override suspend fun saveWeightGoalMetDialogShownEpochDay(epochDay: Long) {}
        override fun observeWeightGoalMetDialogShownTargetKg(): Flow<Float?> = flowOf(null)
        override suspend fun saveWeightGoalMetDialogShownTargetKg(targetKg: Float?) {}
        override suspend fun clearWeightGoalMetDialogShownMarker() {
            error("prefs error")
        }
    }
}
