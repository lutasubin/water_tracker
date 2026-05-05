package com.weappsinc.watertracker.app.feature.water.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveSavedGoalMlUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveSavedUnitUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveWaterGoalMlUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.SaveOnboardingWaterGoalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WaterGoalViewModel(
    private val observeWaterGoalMl: ObserveWaterGoalMlUseCase,
    private val saveOnboardingWaterGoal: SaveOnboardingWaterGoalUseCase,
    private val editMode: Boolean,
    private val observeSavedGoalMl: ObserveSavedGoalMlUseCase,
    private val observeSavedUnit: ObserveSavedUnitUseCase
) : ViewModel() {
    private var editAdjustSeeded = false

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
        if (editMode) {
            viewModelScope.launch {
                observeSavedUnit().collect { u -> u?.let { _unit.value = it } }
            }
            viewModelScope.launch {
                combine(observeSavedGoalMl(), baseGoalMl) { saved, base -> saved to base }
                    .collect { (saved, base) ->
                        // Chỉ đồng bộ adjust một lần khi mở màn sửa; sau đó user chỉnh +/- tự do.
                        if (!editAdjustSeeded && saved != null && base > 0) {
                            _adjustMl.value = (saved - base).coerceAtLeast(-base)
                            editAdjustSeeded = true
                        }
                    }
            }
        }
    }

    fun onStart(onSaved: () -> Unit) {
        viewModelScope.launch {
            val total = _baseGoalMl.value + _adjustMl.value
            saveOnboardingWaterGoal(total, _unit.value)
            onSaved()
        }
    }

    fun onSelectUnit(value: WaterUnit) {
        _unit.value = value
    }

    fun onIncreaseAdjust() {
        _adjustMl.value += ADJUST_STEP_ML
    }

    fun onDecreaseAdjust() {
        val nextAdjustMl = _adjustMl.value - ADJUST_STEP_ML
        _adjustMl.value = nextAdjustMl.coerceAtLeast(-_baseGoalMl.value)
    }

    companion object {
        private const val ADJUST_STEP_ML = 100
    }
}

class WaterGoalViewModelFactory(
    private val observeWaterGoalMl: ObserveWaterGoalMlUseCase,
    private val saveOnboardingWaterGoal: SaveOnboardingWaterGoalUseCase,
    private val editMode: Boolean,
    private val observeSavedGoalMl: ObserveSavedGoalMlUseCase,
    private val observeSavedUnit: ObserveSavedUnitUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        WaterGoalViewModel(
            observeWaterGoalMl = observeWaterGoalMl,
            saveOnboardingWaterGoal = saveOnboardingWaterGoal,
            editMode = editMode,
            observeSavedGoalMl = observeSavedGoalMl,
            observeSavedUnit = observeSavedUnit
        ) as T
}

