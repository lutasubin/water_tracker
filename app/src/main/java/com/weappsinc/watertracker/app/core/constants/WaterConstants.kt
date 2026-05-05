package com.weappsinc.watertracker.app.core.constants

object WaterConstants {
    /** Mỗi lần bấm DRINK cộng thêm (ml). */
    const val DEFAULT_DRINK_ML = 250
    const val DRINK_STEP_ML = 50
    const val MIN_DRINK_ML = 50
    const val MAX_DRINK_ML = 5000
    const val PRESET_DRINK_150 = 150
    const val PRESET_DRINK_200 = 200
    const val PRESET_DRINK_500 = 500
    /** Thời gian chạy số tiến độ (ms) khi tăng lượng uống. */
    const val INTAKE_COUNT_UP_MS = 1200
}
