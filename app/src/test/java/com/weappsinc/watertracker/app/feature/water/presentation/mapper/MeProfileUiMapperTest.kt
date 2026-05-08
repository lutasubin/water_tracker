package com.weappsinc.watertracker.app.feature.water.presentation.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

class MeProfileUiMapperTest {

    @Test
    fun sumsTotalsAcrossDays() {
        val zone = ZoneId.of("UTC")
        val today = LocalDate.now(zone).toEpochDay()
        val first = today - 1
        val s = MeProfileUiMapper.build(
            zone = zone,
            firstInstallEpochDay = first,
            intakeByEpochDay = mapOf(first to 100, today to 300),
            openEpochDays = setOf(first, today),
        )
        assertEquals(400, s.totalDrinkingMl)
        assertEquals(2, s.streakDays)
    }
}
