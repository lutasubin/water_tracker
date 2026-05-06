package com.weappsinc.watertracker.app.feature.weigh.domain

import com.weappsinc.watertracker.app.feature.weigh.domain.util.BmiCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

/** Unit test công thức BMI (kg / m²). */
class BmiCalculatorTest {

    @Test
    fun bmi_170cm_70kg() {
        val bmi = BmiCalculator.computeBmi(170, 70)
        assertEquals(24.22f, bmi, 0.02f)
    }

    @Test
    fun zero_height_returns_zero() {
        assertEquals(0f, BmiCalculator.computeBmi(0, 70))
    }

    @Test
    fun zero_weight_returns_zero() {
        assertEquals(0f, BmiCalculator.computeBmi(170, 0))
    }
}
