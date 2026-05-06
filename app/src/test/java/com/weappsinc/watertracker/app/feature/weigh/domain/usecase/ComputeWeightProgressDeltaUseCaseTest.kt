package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ComputeWeightProgressDeltaUseCaseTest {

    private val useCase = ComputeWeightProgressDeltaUseCase()

    @Test
    fun noLogs_usesFallbackStart() {
        val d = useCase(
            draftKg = 63f,
            latestLog = null,
            beforeLatestLog = null,
            fallbackStartKg = 65f
        )
        assertEquals(-2f, d!!, 0.001f)
    }

    @Test
    fun noLogs_noFallback_returnsNull() {
        val d = useCase(
            draftKg = 63f,
            latestLog = null,
            beforeLatestLog = null,
            fallbackStartKg = null
        )
        assertNull(d)
    }

    @Test
    fun draftDiffersFromLatest_usesLatestAsRef() {
        val latest = WeighLogEntry(60.0, 100L, 0L)
        val d = useCase(
            draftKg = 59.5f,
            latestLog = latest,
            beforeLatestLog = WeighLogEntry(65.0, 99L, 0L),
            fallbackStartKg = 65f
        )
        assertEquals(-0.5f, d!!, 0.001f)
    }

    @Test
    fun draftMatchesLatest_usesPenultimate() {
        val latest = WeighLogEntry(60.0, 102L, 0L)
        val before = WeighLogEntry(60.5, 101L, 0L)
        val d = useCase(
            draftKg = 60f,
            latestLog = latest,
            beforeLatestLog = before,
            fallbackStartKg = 65f
        )
        assertEquals(-0.5f, d!!, 0.001f)
    }

    @Test
    fun draftMatchesLatest_noPenultimate_usesFallback() {
        val latest = WeighLogEntry(60.0, 102L, 0L)
        val d = useCase(
            draftKg = 60f,
            latestLog = latest,
            beforeLatestLog = null,
            fallbackStartKg = 65f
        )
        assertEquals(-5f, d!!, 0.001f)
    }
}
