package com.weappsinc.watertracker.app.feature.tall.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.ObserveTallUseCase
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.SaveTallUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TallViewModel(
    private val observeTall: ObserveTallUseCase,
    private val saveTall: SaveTallUseCase
) : ViewModel() {
    private val _tallCm = MutableStateFlow(DEFAULT_TALL_CM)
    val tallCm = _tallCm.asStateFlow()

    init {
        viewModelScope.launch { observeTall().collect { _tallCm.value = it } }
    }

    fun displayRange(): IntRange {
        return 120..220
    }

    fun displayTallValue(): Int = _tallCm.value

    fun onSelectDisplayTall(value: Int) {
        _tallCm.value = value
    }

    fun saveSelection() {
        viewModelScope.launch { saveTall(_tallCm.value) }
    }

    companion object {
        private const val DEFAULT_TALL_CM = 170
    }
}

class TallViewModelFactory(
    private val observeTall: ObserveTallUseCase,
    private val saveTall: SaveTallUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = TallViewModel(observeTall, saveTall) as T
}
