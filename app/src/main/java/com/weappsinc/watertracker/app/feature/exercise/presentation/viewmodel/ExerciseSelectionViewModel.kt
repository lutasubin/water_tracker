package com.weappsinc.watertracker.app.feature.exercise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.exercise.domain.model.ExerciseLevel
import com.weappsinc.watertracker.app.feature.exercise.domain.usecase.ObserveExerciseLevelUseCase
import com.weappsinc.watertracker.app.feature.exercise.domain.usecase.SaveExerciseLevelUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

class ExerciseSelectionViewModel(
    private val observeExerciseLevel: ObserveExerciseLevelUseCase,
    private val saveExerciseLevel: SaveExerciseLevelUseCase
) : ViewModel() {
    private val saveMutex = Mutex()

    private val _selectedLevel = MutableStateFlow(ExerciseLevel.LOW)
    val selectedLevel = _selectedLevel.asStateFlow()

    init {
        viewModelScope.launch {
            observeExerciseLevel().collect { _selectedLevel.value = it }
        }
    }

    fun onSelectLevel(level: ExerciseLevel) {
        _selectedLevel.value = level
    }

    /** Lưu xong mới onSaved — tránh double-tap Next/nav trùng. */
    fun saveSelection(onSaved: () -> Unit) {
        viewModelScope.launch {
            if (!saveMutex.tryLock()) return@launch
            try {
                saveExerciseLevel(_selectedLevel.value)
                onSaved()
            } finally {
                saveMutex.unlock()
            }
        }
    }
}

class ExerciseSelectionViewModelFactory(
    private val observeExerciseLevel: ObserveExerciseLevelUseCase,
    private val saveExerciseLevel: SaveExerciseLevelUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExerciseSelectionViewModel(observeExerciseLevel, saveExerciseLevel) as T
    }
}
