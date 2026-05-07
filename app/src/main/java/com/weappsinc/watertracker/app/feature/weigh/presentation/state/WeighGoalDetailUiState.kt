package com.weappsinc.watertracker.app.feature.weigh.presentation.state

import com.weappsinc.watertracker.app.feature.weigh.domain.model.BmiCategory
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit

data class WeighGoalDetailUiState(
    val heightCm: Int,
    val targetWeightKg: Float?,
    val displayMassUnit: MassUnit,
    val displayDraftKg: Float,
    val targetValueText: String,
    val gapValueText: String,
    val journeyProgressFraction: Float,
    val journeyProgressPercent: Int,
    val startWeightText: String,
    val progressDeltaValueText: String,
    val progressDeltaFavorable: Boolean,
    val progressDeltaNeutral: Boolean,
    val bmiValueText: String,
    val bmiCategory: BmiCategory,
    val scaleLowEndFraction: Float,
    val scaleNormalEndFraction: Float,
    val bmiIndicatorFraction: Float,
    /** false khi đã có log cho hôm nay — chỉ được ghi nhận 1 lần/ngày. */
    val canEditTodayWeight: Boolean,
    /** false khi không hiện nút Ghi nhận (đã có log trong ngày). */
    val showWeighRecordCta: Boolean,
    val savedBannerTime: String?,
    /** Mốc thời gian lần ghi nhận thành công gần nhất (epoch ms); null nếu chưa có. */
    val lastRecordSuccessMs: Long?,
    val recordError: Boolean,
    val isRecording: Boolean
)
