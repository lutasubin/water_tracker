package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.ObserveTallUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.model.ArchiveCompletedWeightGoalOutcome
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeightGoalCompletionSnapshot
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighPreferencesRepository
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ArchiveCompletedWeightGoalUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ClassifyBmiUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.MapBmiToScaleFractionUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighJourneyStartWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLatestLogUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighTargetWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighJourneyStartWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighTargetWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeighTrackerViewModel(
    weighPrefs: WeighPreferencesRepository,
    private val observeTall: ObserveTallUseCase,
    private val observeWeight: ObserveWeightUseCase,
    private val observeLatestLog: ObserveWeighLatestLogUseCase,
    private val observeMassUnit: ObserveWeighMassUnitUseCase,
    private val saveMassUnit: SaveWeighMassUnitUseCase,
    private val observeTargetWeightKg: ObserveWeighTargetWeightKgUseCase,
    private val saveTargetWeightKg: SaveWeighTargetWeightKgUseCase,
    private val observeJourneyStartWeightKg: ObserveWeighJourneyStartWeightKgUseCase,
    private val saveJourneyStartWeightKg: SaveWeighJourneyStartWeightKgUseCase,
    private val classifyBmi: ClassifyBmiUseCase,
    private val mapBmiFraction: MapBmiToScaleFractionUseCase,
    archiveCompletedWeightGoal: ArchiveCompletedWeightGoalUseCase,
) : ViewModel() {

    private val goalMet =
        WeighTrackerGoalMetCoordinator(weighPrefs, archiveCompletedWeightGoal, viewModelScope)

    init {
        goalMet.startObservingDialogEpoch()
    }

    val uiState = buildWeighTrackerUiStateFlow(
        viewModelScope,
        observeTall,
        observeWeight,
        observeLatestLog,
        observeMassUnit,
        observeTargetWeightKg,
        observeJourneyStartWeightKg,
        classifyBmi,
        mapBmiFraction,
    )
    val lastArchiveOutcome: StateFlow<ArchiveCompletedWeightGoalOutcome?> = goalMet.lastArchiveOutcome
    val lastArchiveFailed: StateFlow<Boolean> = goalMet.lastArchiveFailed

    fun onMassUnitSelected(unit: MassUnit) {
        viewModelScope.launch { saveMassUnit(unit) }
    }

    fun onConfirmTargetJourney(targetKg: Float, currentBodyWeightKg: Float) {
        viewModelScope.launch {
            val t = MassDisplay.snapTargetKg(targetKg)
            saveTargetWeightKg(t)
            saveJourneyStartWeightKg(MassDisplay.snapTargetKg(currentBodyWeightKg))
        }
    }

    fun shouldShowWeightTargetMetDialog(todayEpoch: Long, isTargetMet: Boolean): Boolean =
        goalMet.shouldShowWeightTargetMetDialog(todayEpoch, isTargetMet)

    fun markWeightTargetMetDialogShown(todayEpoch: Long) {
        goalMet.markWeightTargetMetDialogShown(todayEpoch)
    }

    fun onWeightGoalMetDialogDismissed(snapshot: WeightGoalCompletionSnapshot) {
        goalMet.onWeightGoalMetDialogDismissed(snapshot)
    }
}
