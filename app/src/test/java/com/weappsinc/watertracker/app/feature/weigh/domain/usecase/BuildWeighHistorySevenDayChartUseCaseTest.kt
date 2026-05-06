package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import org.junit.Assert.assertEquals
import org.junit.Test

class BuildWeighHistorySevenDayChartUseCaseTest {

    private val useCase = BuildWeighHistorySevenDayChartUseCase()

    @Test
    fun narrowWindowFromInstall_coversOnlyDaysSinceStart() {
        val today = 100L
        val windowStart = 98L
        val logs = listOf(
            WeighLogEntry(70.0, 1L, 98),
            WeighLogEntry(69.0, 2L, 100)
        )
        val out = useCase(logs, windowStart, today)
        assertEquals(listOf(98L, 99L, 100L), out.map { it.epochDay })
        assertEquals(70f, out[0].weightKg, 0.001f)
        assertEquals(70f, out[1].weightKg, 0.001f)
        assertEquals(69f, out[2].weightKg, 0.001f)
    }

    @Test
    fun fullWeekWindow_sevenPoints() {
        val today = 10L
        val windowStart = 4L
        val logs = listOf(WeighLogEntry(65.0, 1L, 4))
        val out = useCase(logs, windowStart, today)
        assertEquals(7, out.size)
        assertEquals(4L, out.first().epochDay)
        assertEquals(10L, out.last().epochDay)
    }
}
