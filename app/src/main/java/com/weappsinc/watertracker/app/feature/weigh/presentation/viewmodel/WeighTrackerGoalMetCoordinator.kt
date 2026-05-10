package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import com.weappsinc.watertracker.app.feature.weigh.domain.model.ArchiveCompletedWeightGoalOutcome
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeightGoalCompletionSnapshot
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighPreferencesRepository
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ArchiveCompletedWeightGoalUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Thu thập epoch popup đạt mục tiêu + archive khi đóng dialog. */
internal class WeighTrackerGoalMetCoordinator(
    private val weighPrefs: WeighPreferencesRepository,
    private val archiveCompletedWeightGoal: ArchiveCompletedWeightGoalUseCase,
    private val scope: CoroutineScope,
) {
    private var weightGoalMetDialogShownEpoch: Long = Long.MIN_VALUE
    private val _lastArchiveOutcome = MutableStateFlow<ArchiveCompletedWeightGoalOutcome?>(null)
    private val _lastArchiveFailed = MutableStateFlow(false)
    val lastArchiveOutcome: StateFlow<ArchiveCompletedWeightGoalOutcome?> = _lastArchiveOutcome.asStateFlow()
    val lastArchiveFailed: StateFlow<Boolean> = _lastArchiveFailed.asStateFlow()

    fun startObservingDialogEpoch() {
        scope.launch {
            weighPrefs.observeWeightGoalMetDialogShownEpochDay().collect { epoch ->
                weightGoalMetDialogShownEpoch = epoch ?: Long.MIN_VALUE
            }
        }
    }

    fun shouldShowWeightTargetMetDialog(todayEpoch: Long, isTargetMet: Boolean): Boolean {
        if (!isTargetMet) return false
        return weightGoalMetDialogShownEpoch != todayEpoch
    }

    fun markWeightTargetMetDialogShown(todayEpoch: Long) {
        weightGoalMetDialogShownEpoch = todayEpoch
        scope.launch {
            weighPrefs.saveWeightGoalMetDialogShownEpochDay(todayEpoch)
        }
    }

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
