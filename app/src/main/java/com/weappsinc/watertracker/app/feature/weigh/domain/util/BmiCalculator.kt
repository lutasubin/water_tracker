package com.weappsinc.watertracker.app.feature.weigh.domain.util

/** BMI = kg / (m^2), chiều cao cm. */
object BmiCalculator {
    fun computeBmi(heightCm: Int, weightKg: Int): Float {
        if (heightCm <= 0 || weightKg <= 0) return 0f
        val m = heightCm / 100f
        return weightKg / (m * m)
    }

    fun computeBmi(heightCm: Int, weightKg: Float): Float {
        if (heightCm <= 0 || weightKg <= 0f) return 0f
        val m = heightCm / 100f
        return weightKg / (m * m)
    }
}
