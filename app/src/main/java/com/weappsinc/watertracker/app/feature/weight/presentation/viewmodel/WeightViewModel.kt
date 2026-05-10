package com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeightProfileAndWeighLogUseCase
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

class WeightViewModel(
    private val observeWeight: ObserveWeightUseCase,
    private val saveWeightProfileAndWeighLog: SaveWeightProfileAndWeighLogUseCase
) : ViewModel() {
    private val saveMutex = Mutex()

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

    /**
     * Hướng B: ghi log (timestamp) rồi mirror cân hồ sơ.
     * Lưu xong mới onSaved — tránh double-tap Next/pop trùng.
     */
    fun saveSelection(onSaved: () -> Unit) {
        viewModelScope.launch {
            if (!saveMutex.tryLock()) return@launch
            try {
                val r = saveWeightProfileAndWeighLog(_weightKg.value.toFloat())
                if (r.isSuccess) onSaved()
            } finally {
                saveMutex.unlock()
            }
        }
    }

    companion object {
        private const val DEFAULT_WEIGHT_KG = 65
    }
}

class WeightViewModelFactory(
    private val observeWeight: ObserveWeightUseCase,
    private val saveWeightProfileAndWeighLog: SaveWeightProfileAndWeighLogUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        WeightViewModel(observeWeight, saveWeightProfileAndWeighLog) as T
}
