package com.weappsinc.watertracker.app.feature.weigh.domain.util

/** Tiến độ hành trình cân nặng: 0…1 (điểm bắt đầu → mục tiêu). */
object WeighJourneyProgress {
    fun fraction(startKg: Float, currentKg: Float, targetKg: Float): Float {
        if (startKg == targetKg) return 1f
        return if (targetKg < startKg) {
            ((startKg - currentKg) / (startKg - targetKg)).coerceIn(0f, 1f)
        } else {
            ((currentKg - startKg) / (targetKg - startKg)).coerceIn(0f, 1f)
        }
    }
}
