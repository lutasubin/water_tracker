package com.weappsinc.watertracker.app.feature.weigh.domain

import com.weappsinc.watertracker.app.feature.weigh.domain.model.BmiCategory
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ClassifyBmiUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

/** Kiểm tra ngưỡng phân loại BMI hiển thị. */
class ClassifyBmiUseCaseTest {

    private val useCase = ClassifyBmiUseCase()

    @Test
    fun under_18_5_is_underweight() {
        assertEquals(BmiCategory.Underweight, useCase(18.4f))
    }

    @Test
    fun `18_5_is_normal`() {
        assertEquals(BmiCategory.Normal, useCase(18.5f))
    }

    @Test
    fun between_18_5_and_25_is_normal() {
        assertEquals(BmiCategory.Normal, useCase(22f))
    }

    @Test
    fun `24_9_is_normal`() {
        assertEquals(BmiCategory.Normal, useCase(24.9f))
    }

    @Test
    fun `25_is_overweight`() {
        assertEquals(BmiCategory.Overweight, useCase(25f))
    }

    @Test
    fun zero_bmi_defaults_normal() {
        assertEquals(BmiCategory.Normal, useCase(0f))
    }
}
