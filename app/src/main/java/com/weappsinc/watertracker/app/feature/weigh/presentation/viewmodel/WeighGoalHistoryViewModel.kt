package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveCompletedWeightGoalsUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.presentation.mapper.WeighGoalHistoryUiMapper
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighGoalHistoryUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class WeighGoalHistoryViewModel(
    observeCompleted: ObserveCompletedWeightGoalsUseCase,
    observeMassUnit: ObserveWeighMassUnitUseCase,
) : ViewModel() {

    val uiState = combine(
        observeCompleted(),
        observeMassUnit(),
    ) { goals, unit ->
        WeighGoalHistoryUiMapper.map(goals, unit)
    }.catch {
        emit(WeighGoalHistoryUiState.Error(displayUnit = MassUnit.KG))
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        WeighGoalHistoryUiState.Loading,
    )
}

class WeighGoalHistoryViewModelFactory(
    private val observeCompleted: ObserveCompletedWeightGoalsUseCase,
    private val observeMassUnit: ObserveWeighMassUnitUseCase,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        WeighGoalHistoryViewModel(observeCompleted, observeMassUnit) as T
}
