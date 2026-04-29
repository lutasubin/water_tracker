package com.weappsinc.watertracker.app.feature.water.domain

import com.weappsinc.watertracker.app.feature.exercise.domain.model.ExerciseLevel
import kotlin.math.max

object WaterGoalCalculator {
    /**
     * Công thức ước lượng goal nước theo heuristics (ml/ngày).
     *
     * - Base: weight(kg) * 35ml
     * - Height adjust: (heightCm - 150) * 2ml (không âm)
     * - Age factor:
     *   + < 18 tuổi: 1.05
     *   + 18..55: 1.00
     *   + > 55 tuổi: 0.95
     * - Exercise factor:
     *   + LOW: 1.00
     *   + MODERATE: 1.10
     *   + HIGH: 1.20
     *
     * Làm tròn về bội số 10ml.
     */
    fun calculateDailyGoalMl(
        ageYears: Int,
        heightCm: Int,
        weightKg: Int,
        exerciseLevel: ExerciseLevel,
    ): Int {
        val baseMl = weightKg * 35
        val heightAdjMl = max(0, (heightCm - 150) * 2)

        val ageMultiplier = when {
            ageYears < 18 -> 1.05
            ageYears <= 55 -> 1.0
            else -> 0.95
        }
        val exerciseMultiplier = when (exerciseLevel) {
            ExerciseLevel.LOW -> 1.0
            ExerciseLevel.MODERATE -> 1.10
            ExerciseLevel.HIGH -> 1.20
        }

        val total = (baseMl + heightAdjMl) * ageMultiplier * exerciseMultiplier
        val rounded = ((total + 5) / 10).toInt() * 10

        return rounded.coerceIn(1200, 6000)
    }
}

