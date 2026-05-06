package com.weappsinc.watertracker.app.feature.water.presentation.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.ZoneId

class MeProfileUiMapperTest {

    @Test
    fun sumsTotalsAcrossDays() {
        val s = MeProfileUiMapper.build(
            zone = ZoneId.of("UTC"),
            firstInstallEpochDay = 0L,
            goalMl = null,
            intakeByEpochDay = mapOf(1L to 100, 2L to 300),
        )
        assertEquals(400, s.totalDrinkingMl)
    }
}
