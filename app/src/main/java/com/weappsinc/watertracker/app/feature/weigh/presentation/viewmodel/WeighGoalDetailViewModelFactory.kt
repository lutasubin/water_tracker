package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.ObserveTallUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ClassifyBmiUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ComputeWeightProgressDeltaUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.MapBmiToScaleFractionUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighJourneyStartWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLatestLogForTodayUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLatestTwoLogsUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighTargetWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighLogUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighTargetWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.SaveWeightUseCase

class WeighGoalDetailViewModelFactory(
    private val observeTall: ObserveTallUseCase,
    private val observeWeight: ObserveWeightUseCase,
    private val observeMassUnit: ObserveWeighMassUnitUseCase,
    private val saveMassUnit: SaveWeighMassUnitUseCase,
    private val observeTargetWeightKg: ObserveWeighTargetWeightKgUseCase,
    private val saveTargetWeightKg: SaveWeighTargetWeightKgUseCase,
    private val observeJourneyStartWeightKg: ObserveWeighJourneyStartWeightKgUseCase,
    private val observeLatestTwoLogs: ObserveWeighLatestTwoLogsUseCase,
    private val observeLatestLogForToday: ObserveWeighLatestLogForTodayUseCase,
    private val saveWeighLog: SaveWeighLogUseCase,
    private val saveWeightProfile: SaveWeightUseCase,
    private val computeDelta: ComputeWeightProgressDeltaUseCase,
    private val classifyBmi: ClassifyBmiUseCase,
    private val mapBmiFraction: MapBmiToScaleFractionUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        WeighGoalDetailViewModel(
            observeTall = observeTall,
            observeWeight = observeWeight,
            observeMassUnit = observeMassUnit,
            saveMassUnit = saveMassUnit,
            observeTargetWeightKg = observeTargetWeightKg,
            saveTargetWeightKg = saveTargetWeightKg,
            observeJourneyStartWeightKg = observeJourneyStartWeightKg,
            observeLatestTwoLogs = observeLatestTwoLogs,
            observeLatestLogForToday = observeLatestLogForToday,
            saveWeighLog = saveWeighLog,
            saveWeightProfile = saveWeightProfile,
            computeDelta = computeDelta,
            classifyBmi = classifyBmi,
            mapBmiFraction = mapBmiFraction
        ) as T
}
