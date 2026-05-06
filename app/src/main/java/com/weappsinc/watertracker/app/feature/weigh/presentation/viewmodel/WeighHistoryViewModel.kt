package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.BuildWeighHistorySevenDayChartUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLogsLast7DaysUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.presentation.mapper.WeighHistoryUiMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.ZoneId

class WeighHistoryViewModel(
    private val observeMassUnit: ObserveWeighMassUnitUseCase,
    private val observeLast7: ObserveWeighLogsLast7DaysUseCase,
    private val buildChart: BuildWeighHistorySevenDayChartUseCase
) : ViewModel() {

    val uiState = combine(observeMassUnit(), observeLast7()) { unit, logs ->
        val today = LocalDate.now(ZoneId.systemDefault()).toEpochDay()
        val chart = buildChart(logs, today)
        WeighHistoryUiMapper.map(
            logsDesc = logs,
            chartPoints = chart,
            todayEpochDay = today,
            unit = unit
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        WeighHistoryUiMapper.map(
            logsDesc = emptyList(),
            chartPoints = emptyList(),
            todayEpochDay = LocalDate.now(ZoneId.systemDefault()).toEpochDay(),
            unit = MassUnit.KG
        )
    )
}
