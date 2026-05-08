package com.weappsinc.watertracker.app.feature.age.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.age.domain.usecase.ObserveAgeUseCase
import com.weappsinc.watertracker.app.feature.age.domain.usecase.SaveAgeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

class AgeViewModel(private val observeAge: ObserveAgeUseCase, private val saveAge: SaveAgeUseCase) : ViewModel() {
    private val saveMutex = Mutex()
    private val _age = MutableStateFlow(26)
    val age = _age.asStateFlow()

    init {
        viewModelScope.launch { observeAge().collect { _age.value = it } }
    }

    fun onSelectAge(value: Int) {
        _age.value = value
    }

    /** Lưu xong mới onSaved — tránh double-tap Next/nav trùng. */
    fun saveSelection(onSaved: () -> Unit) {
        viewModelScope.launch {
            if (!saveMutex.tryLock()) return@launch
            try {
                saveAge(_age.value)
                onSaved()
            } finally {
                saveMutex.unlock()
            }
        }
    }
}

class AgeViewModelFactory(private val observeAge: ObserveAgeUseCase, private val saveAge: SaveAgeUseCase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = AgeViewModel(observeAge, saveAge) as T
}
