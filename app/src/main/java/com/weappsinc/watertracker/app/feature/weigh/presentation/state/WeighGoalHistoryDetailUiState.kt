package com.weappsinc.watertracker.app.feature.weigh.presentation.state

import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighHistoryChartPoint

sealed interface WeighGoalHistoryDetailUiState {
    data object Loading : WeighGoalHistoryDetailUiState
    data class Error(val displayUnit: MassUnit) : WeighGoalHistoryDetailUiState
    data class Empty(val displayUnit: MassUnit) : WeighGoalHistoryDetailUiState
    data class Content(
        val displayUnit: MassUnit,
        val targetValueText: String,
        val achievedValueText: String,
        val startWeightText: String,
        val progressDeltaValueText: String,
        val progressDeltaFavorable: Boolean,
        val progressDeltaNeutral: Boolean,
        val completedAtText: String,
        val chartPoints: List<WeighHistoryChartPoint>,
        val xLabels: List<String>,
    ) : WeighGoalHistoryDetailUiState
}
