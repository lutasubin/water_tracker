package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.exception.WeighDayAlreadyLoggedException
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository
import java.time.LocalDate
import java.time.ZoneId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SaveWeighLogUseCaseTest {

    @Test
    fun dayAlreadyHasLog_returnsFailureWithoutInsert() = runBlocking {
        val today = LocalDate.now(ZoneId.systemDefault()).toEpochDay()
        val repo = FakeRepo().apply { counts[today] = 1 }
        val r = SaveWeighLogUseCase(repo).invoke(70f)
        assertTrue(r.isFailure)
        assertTrue(r.exceptionOrNull() is WeighDayAlreadyLoggedException)
        assertEquals(0, repo.insertCount)
    }

    @Test
    fun emptyDay_callsInsertOnce() = runBlocking {
        val repo = FakeRepo()
        assertTrue(SaveWeighLogUseCase(repo).invoke(70f).isSuccess)
        assertEquals(1, repo.insertCount)
    }

    /** Repo giả lập: đếm log theo ngày + số lần insert. */
    private class FakeRepo(
        val counts: MutableMap<Long, Int> = mutableMapOf(),
        var insertCount: Int = 0
    ) : WeighLogRepository {
        override fun observeLatestTwoDesc(): Flow<List<WeighLogEntry>> = flowOf(emptyList())
        override fun observeLatestLog(): Flow<WeighLogEntry?> = flowOf(null)
        override fun observeLatestForEpochDay(epochDay: Long): Flow<WeighLogEntry?> = flowOf(null)
        override fun observeLogsBetweenEpochDays(startEpochDay: Long, endEpochDay: Long): Flow<List<WeighLogEntry>> =
            flowOf(emptyList())

        override suspend fun insertLog(epochDay: Long, weightKg: Double, recordedAtMs: Long): Result<Unit> {
            insertCount++
            return Result.success(Unit)
        }

        override suspend fun countLogsForEpochDay(epochDay: Long): Int = counts[epochDay] ?: 0
    }
}
