package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveFirstInstallEpochDayUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.BuildWeighHistorySevenDayChartUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLogsLast7DaysUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase

class WeighHistoryViewModelFactory(
    private val observeMassUnit: ObserveWeighMassUnitUseCase,
    private val observeLast7: ObserveWeighLogsLast7DaysUseCase,
    private val observeFirstInstallEpochDay: ObserveFirstInstallEpochDayUseCase,
    private val buildChart: BuildWeighHistorySevenDayChartUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        WeighHistoryViewModel(
            observeMassUnit = observeMassUnit,
            observeLast7 = observeLast7,
            observeFirstInstallEpochDay = observeFirstInstallEpochDay,
            buildChart = buildChart
        ) as T
}
