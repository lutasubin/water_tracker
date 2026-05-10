package com.weappsinc.watertracker.app.feature.water.presentation.state

import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit

/** UI màn Me: tổng ml + streak + menu + hàng chỉnh hồ sơ. */
data class MeProfileUiState(
    val totalDrinkingMl: Int,
    val streakDays: Int,
    val heightValueText: String,
    val weightValueText: String,
    val ageValueText: String,
    val sex: GenderType,
    val displayMassUnit: MassUnit,
)
