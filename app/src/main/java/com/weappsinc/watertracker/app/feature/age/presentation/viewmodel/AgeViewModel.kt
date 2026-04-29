package com.weappsinc.watertracker.app.feature.age.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.age.domain.usecase.ObserveAgeUseCase
import com.weappsinc.watertracker.app.feature.age.domain.usecase.SaveAgeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AgeViewModel(private val observeAge: ObserveAgeUseCase, private val saveAge: SaveAgeUseCase) : ViewModel() {
    private val _age = MutableStateFlow(26)
    val age = _age.asStateFlow()

    init {
        viewModelScope.launch { observeAge().collect { _age.value = it } }
    }

    fun onSelectAge(value: Int) {
        _age.value = value
    }

    fun saveSelection() {
        viewModelScope.launch { saveAge(_age.value) }
    }
}

class AgeViewModelFactory(private val observeAge: ObserveAgeUseCase, private val saveAge: SaveAgeUseCase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = AgeViewModel(observeAge, saveAge) as T
}
