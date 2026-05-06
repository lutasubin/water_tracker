package com.weappsinc.watertracker.app.feature.weigh.presentation.state

import com.weappsinc.watertracker.app.feature.weigh.domain.model.BmiCategory
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit

data class WeighTrackerUiState(
    val heightCm: Int,
    val bodyWeightKg: Float,
    val heightValueText: String,
    val weightValueText: String,
    val displayMassUnit: MassUnit,
    val bmiValueText: String,
    val bmiCategory: BmiCategory,
    val scaleLowEndFraction: Float,
    val scaleNormalEndFraction: Float,
    val bmiIndicatorFraction: Float,
    val targetWeightKg: Float?,
    val targetValueText: String?,
    val gapValueText: String,
    val journeyProgressFraction: Float,
    val journeyProgressPercent: Int,
)
