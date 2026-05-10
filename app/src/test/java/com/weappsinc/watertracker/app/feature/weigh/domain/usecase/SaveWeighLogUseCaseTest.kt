package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SaveWeighLogUseCaseTest {

    @Test
    fun emptyDay_callsInsertOnce() = runBlocking {
        val repo = FakeRepo()
        assertTrue(SaveWeighLogUseCase(repo).invoke(70f).isSuccess)
        assertEquals(1, repo.insertCount)
    }

    @Test
    fun sameDaySecondCall_insertsAgain() = runBlocking {
        val repo = FakeRepo().apply { insertCount = 1 }
        assertTrue(SaveWeighLogUseCase(repo).invoke(71f).isSuccess)
        assertEquals(2, repo.insertCount)
    }

    private class FakeRepo(var insertCount: Int = 0) : WeighLogRepository {
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
}
