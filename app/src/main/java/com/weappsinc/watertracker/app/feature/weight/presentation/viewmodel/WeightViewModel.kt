package com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.SaveWeightUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeightViewModel(
    private val observeWeight: ObserveWeightUseCase,
    private val saveWeight: SaveWeightUseCase
) : ViewModel() {
    private val _weightKg = MutableStateFlow(DEFAULT_WEIGHT_KG)
    val weightKg = _weightKg.asStateFlow()

    init {
        viewModelScope.launch { observeWeight().collect { _weightKg.value = it } }
    }

    fun displayRange(): IntRange {
        return 30..200
    }

    fun displayWeightValue(): Int {
        return _weightKg.value
    }

    fun onSelectDisplayWeight(value: Int) {
        _weightKg.value = value
    }

    fun saveSelection() {
        viewModelScope.launch { saveWeight(_weightKg.value) }
    }

    companion object {
        private const val DEFAULT_WEIGHT_KG = 65
    }
}

class WeightViewModelFactory(
    private val observeWeight: ObserveWeightUseCase,
    private val saveWeight: SaveWeightUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = WeightViewModel(observeWeight, saveWeight) as T
}
