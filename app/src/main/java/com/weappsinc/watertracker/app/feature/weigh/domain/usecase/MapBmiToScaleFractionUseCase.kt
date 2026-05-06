package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.util.BmiScaleGeometry

/** Vị trí chỉ báo BMI trên trục cố định 15–35 → fraction 0…1. */
class MapBmiToScaleFractionUseCase {
    operator fun invoke(bmi: Float): Float = BmiScaleGeometry.indicatorFraction(bmi)
}
