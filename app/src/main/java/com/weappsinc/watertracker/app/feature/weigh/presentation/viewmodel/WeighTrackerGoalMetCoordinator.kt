package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import com.weappsinc.watertracker.app.feature.weigh.domain.model.ArchiveCompletedWeightGoalOutcome
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeightGoalCompletionSnapshot
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ArchiveCompletedWeightGoalUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Archive mục tiêu cân nặng khi user dismiss popup hoàn thành; expose outcome cho UI. */
internal class WeighTrackerGoalMetCoordinator(
    private val archiveCompletedWeightGoal: ArchiveCompletedWeightGoalUseCase,
    private val scope: CoroutineScope,
) {
    private val _lastArchiveOutcome = MutableStateFlow<ArchiveCompletedWeightGoalOutcome?>(null)
    private val _lastArchiveFailed = MutableStateFlow(false)
    val lastArchiveOutcome: StateFlow<ArchiveCompletedWeightGoalOutcome?> = _lastArchiveOutcome.asStateFlow()
    val lastArchiveFailed: StateFlow<Boolean> = _lastArchiveFailed.asStateFlow()

    fun onWeightGoalMetDialogDismissed(snapshot: WeightGoalCompletionSnapshot) {
        scope.launch {
            archiveCompletedWeightGoal(snapshot)
                .onSuccess {
                    _lastArchiveOutcome.value = it
                    _lastArchiveFailed.value = !it.preferencesCleared
                }
                .onFailure {
                    _lastArchiveOutcome.value = null
                    _lastArchiveFailed.value = true
                }
        }
    }
}
