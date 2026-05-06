package com.weappsinc.watertracker.app.feature.weigh.domain

import com.weappsinc.watertracker.app.feature.weigh.domain.util.WeighJourneyProgress
import org.junit.Assert.assertEquals
import org.junit.Test

class WeighJourneyProgressTest {
    @Test
    fun lose_weight_halfway() {
        val p = WeighJourneyProgress.fraction(startKg = 80f, currentKg = 75f, targetKg = 70f)
        assertEquals(0.5f, p, 0.01f)
    }

    @Test
    fun gain_weight_quarter() {
        val p = WeighJourneyProgress.fraction(startKg = 60f, currentKg = 62f, targetKg = 68f)
        assertEquals(0.25f, p, 0.01f)
    }
}
