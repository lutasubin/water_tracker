package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveCompletedGoalDetailUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.presentation.mapper.WeighGoalHistoryDetailUiMapper
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighGoalHistoryDetailUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class WeighGoalHistoryDetailViewModel(
    observeCompletedDetail: ObserveCompletedGoalDetailUseCase,
    observeMassUnit: ObserveWeighMassUnitUseCase,
    goalId: Long,
) : ViewModel() {
    val uiState = combine(
        observeCompletedDetail(goalId),
        observeMassUnit(),
    ) { detail, unit ->
        WeighGoalHistoryDetailUiMapper.map(detail, unit)
    }.catch {
        emit(WeighGoalHistoryDetailUiState.Error(displayUnit = MassUnit.KG))
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        WeighGoalHistoryDetailUiState.Loading,
    )
}

class WeighGoalHistoryDetailViewModelFactory(
    private val observeCompletedDetail: ObserveCompletedGoalDetailUseCase,
    private val observeMassUnit: ObserveWeighMassUnitUseCase,
    private val goalId: Long,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        WeighGoalHistoryDetailViewModel(
            observeCompletedDetail = observeCompletedDetail,
            observeMassUnit = observeMassUnit,
            goalId = goalId,
        ) as T
}
