package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository
import com.weappsinc.watertracker.app.feature.weight.domain.repository.WeightRepository
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.SaveWeightUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SaveWeightProfileAndWeighLogUseCaseTest {

    @Test
    fun logFails_profileNotSaved() = runBlocking {
        val repo = FailingLogRepo()
        val profile = FakeWeightRepo()
        val uc = SaveWeightProfileAndWeighLogUseCase(
            SaveWeighLogUseCase(repo),
            SaveWeightUseCase(profile)
        )
        assertTrue(uc.invoke(70.5f).isFailure)
        assertNull(profile.savedWeight)
    }

    @Test
    fun success_insertsLogThenSnappedProfile() = runBlocking {
        val logRepo = CountingLogRepo()
        val profile = FakeWeightRepo()
        val uc = SaveWeightProfileAndWeighLogUseCase(
            SaveWeighLogUseCase(logRepo),
            SaveWeightUseCase(profile)
        )
        assertTrue(uc.invoke(70.3f).isSuccess)
        assertEquals(1, logRepo.insertCount)
        assertEquals(71, profile.savedWeight) // snapTargetKg(70.3)=70.5f, roundToInt=71
    }

    private class FailingLogRepo : WeighLogRepository {
        override fun observeLatestTwoDesc(): Flow<List<WeighLogEntry>> = flowOf(emptyList())
        override fun observeLatestLog(): Flow<WeighLogEntry?> = flowOf(null)
        override fun observeLatestForEpochDay(epochDay: Long): Flow<WeighLogEntry?> = flowOf(null)
        override fun observeLogsBetweenEpochDays(startEpochDay: Long, endEpochDay: Long): Flow<List<WeighLogEntry>> =
            flowOf(emptyList())

        override suspend fun insertLog(epochDay: Long, weightKg: Double, recordedAtMs: Long): Result<Unit> =
            Result.failure(RuntimeException("db"))
    }

    private class CountingLogRepo : WeighLogRepository {
        var insertCount = 0
        override fun observeLatestTwoDesc(): Flow<List<WeighLogEntry>> = flowOf(emptyList())
        override fun observeLatestLog(): Flow<WeighLogEntry?> = flowOf(null)
        override fun observeLatestForEpochDay(epochDay: Long): Flow<WeighLogEntry?> = flowOf(null)
        override fun observeLogsBetweenEpochDays(startEpochDay: Long, endEpochDay: Long): Flow<List<WeighLogEntry>> =
            flowOf(emptyList())

        override suspend fun insertLog(epochDay: Long, weightKg: Double, recordedAtMs: Long): Result<Unit> {
            insertCount++
            return Result.success(Unit)
        }
    }

    private class FakeWeightRepo : WeightRepository {
        var savedWeight: Int? = null
        override fun observeWeight(): Flow<Int> = flowOf(65)
        override suspend fun saveWeight(weight: Int) {
            savedWeight = weight
        }
    }
}
