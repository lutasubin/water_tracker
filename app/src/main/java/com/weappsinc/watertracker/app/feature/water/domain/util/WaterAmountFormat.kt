package com.weappsinc.watertracker.app.feature.water.domain.util

import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit
import kotlin.math.roundToInt

object WaterAmountFormat {
    fun format(valueMl: Int, unit: WaterUnit): String = when (unit) {
        WaterUnit.ML -> "$valueMl"
        WaterUnit.L -> "${(valueMl / 1000f * 10f).roundToInt() / 10f}"
    }
}
