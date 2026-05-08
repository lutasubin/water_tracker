package com.weappsinc.watertracker.app.feature.gender.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import com.weappsinc.watertracker.app.feature.gender.domain.usecase.ObserveSelectedGenderUseCase
import com.weappsinc.watertracker.app.feature.gender.domain.usecase.SaveSelectedGenderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

class GenderViewModel(
    private val observeSelectedGenderUseCase: ObserveSelectedGenderUseCase,
    private val saveSelectedGenderUseCase: SaveSelectedGenderUseCase
) : ViewModel() {
    private val saveMutex = Mutex()

    private val _selectedGender = MutableStateFlow(GenderType.MALE)
    val selectedGender = _selectedGender.asStateFlow()

    init {
        viewModelScope.launch {
            observeSelectedGenderUseCase().collect { _selectedGender.value = it }
        }
    }

    fun onSelectGender(gender: GenderType) {
        _selectedGender.update { gender }
    }

    /** Lưu xong mới onSaved — tránh double-tap Next/nav trùng. */
    fun saveSelection(onSaved: () -> Unit) {
        viewModelScope.launch {
            if (!saveMutex.tryLock()) return@launch
            try {
                saveSelectedGenderUseCase(_selectedGender.value)
                onSaved()
            } finally {
                saveMutex.unlock()
            }
        }
    }
}

class GenderViewModelFactory(
    private val observeSelectedGenderUseCase: ObserveSelectedGenderUseCase,
    private val saveSelectedGenderUseCase: SaveSelectedGenderUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GenderViewModel(
            observeSelectedGenderUseCase = observeSelectedGenderUseCase,
            saveSelectedGenderUseCase = saveSelectedGenderUseCase
        ) as T
    }
}
