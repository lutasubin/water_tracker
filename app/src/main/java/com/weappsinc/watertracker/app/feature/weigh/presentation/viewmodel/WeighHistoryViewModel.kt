package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveFirstInstallEpochDayUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.BuildWeighHistorySevenDayChartUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLogsLast7DaysUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.presentation.mapper.WeighHistoryUiMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.ZoneId

class WeighHistoryViewModel(
    private val observeMassUnit: ObserveWeighMassUnitUseCase,
    private val observeLast7: ObserveWeighLogsLast7DaysUseCase,
    private val observeFirstInstallEpochDay: ObserveFirstInstallEpochDayUseCase,
    private val buildChart: BuildWeighHistorySevenDayChartUseCase
) : ViewModel() {

    val uiState = combine(
        observeMassUnit(),
        observeLast7(),
        observeFirstInstallEpochDay()
    ) { unit, logs, firstInstallEpoch ->
        val today = LocalDate.now(ZoneId.systemDefault()).toEpochDay()
        val last7Start = today - SEVEN_DAY_SPAN
        val windowStart = maxOf(firstInstallEpoch ?: last7Start, last7Start)
        val chart = buildChart(logs, windowStart, today)
        WeighHistoryUiMapper.map(
            logsDesc = logs,
            chartPoints = chart,
            unit = unit
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        WeighHistoryUiMapper.map(
            logsDesc = emptyList(),
            chartPoints = emptyList(),
            unit = MassUnit.KG
        )
    )

    companion object {
        private const val SEVEN_DAY_SPAN = 6L
    }
}
