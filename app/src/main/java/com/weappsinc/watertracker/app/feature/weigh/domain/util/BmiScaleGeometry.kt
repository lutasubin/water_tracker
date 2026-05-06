package com.weappsinc.watertracker.app.feature.weigh.domain.util

/** Tỉ lệ đoạn màu BMI trên trục 15–35 (WHO 18.5 / 25) + vị trí chỉ báo. */
object BmiScaleGeometry {
    private const val MIN = 15f
    private const val MAX = 35f
    private val range: Float get() = MAX - MIN

    /** Kết thúc vùng gầy / bình thường theo fraction chiều rộng (0..1). */
    fun segmentThresholds(): Pair<Float, Float> {
        val lowEnd = (18.5f - MIN) / range
        val normalEnd = (25f - MIN) / range
        return lowEnd to normalEnd
    }

    fun indicatorFraction(bmi: Float): Float = ((bmi - MIN) / range).coerceIn(0f, 1f)
}
