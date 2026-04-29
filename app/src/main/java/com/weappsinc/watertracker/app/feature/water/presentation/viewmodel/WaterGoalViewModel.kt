package com.weappsinc.watertracker.app.feature.water.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveWaterGoalMlUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted

enum class WaterUnit { ML, L }

class WaterGoalViewModel(
    private val observeWaterGoalMl: ObserveWaterGoalMlUseCase
) : ViewModel() {
    private val _baseGoalMl = MutableStateFlow(0)
    val baseGoalMl = _baseGoalMl.asStateFlow()

    private val _adjustMl = MutableStateFlow(0)
    val adjustMl = _adjustMl.asStateFlow()

    private val _unit = MutableStateFlow(WaterUnit.ML)
    val unit = _unit.asStateFlow()

    val totalGoalMl = combine(baseGoalMl, adjustMl) { goalMl, adjust -> goalMl + adjust }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        0
    )

    init {
        viewModelScope.launch {
            observeWaterGoalMl().collect { _baseGoalMl.value = it }
        }
    }

    fun onSelectUnit(value: WaterUnit) {
        _unit.value = value
    }

    fun onAdjust() {
        _adjustMl.value += DEFAULT_ADJUST_ML
    }

    companion object {
        private const val DEFAULT_ADJUST_ML = 250
    }
}

class WaterGoalViewModelFactory(
    private val observeWaterGoalMl: ObserveWaterGoalMlUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        WaterGoalViewModel(observeWaterGoalMl) as T
}

