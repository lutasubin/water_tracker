package com.weappsinc.watertracker.app.feature.weigh.presentation.state

import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighHistoryChartPoint

data class WeighHistoryRowUi(
    val timeLabel: String,
    val weightValueText: String,
    val deltaSignedText: String?,
    val deltaIsIncrease: Boolean
)

data class WeighHistoryUiState(
    val massUnit: MassUnit,
    val chartPoints: List<WeighHistoryChartPoint>,
    val xLabels: List<String>,
    val listRows: List<WeighHistoryRowUi>,
    val hasChartData: Boolean
)
