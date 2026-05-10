package com.weappsinc.watertracker.app.feature.weigh.presentation.state

import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit

data class WeighGoalHistoryRowUi(
    val id: Long,
    val targetValueText: String,
    val achievedValueText: String,
    val deltaValueText: String,
    val completedAtText: String,
)

sealed interface WeighGoalHistoryUiState {
    data object Loading : WeighGoalHistoryUiState
    data class Error(val displayUnit: MassUnit) : WeighGoalHistoryUiState
    data class Empty(val displayUnit: MassUnit) : WeighGoalHistoryUiState
    data class Content(
        val displayUnit: MassUnit,
        val rows: List<WeighGoalHistoryRowUi>,
    ) : WeighGoalHistoryUiState
}
