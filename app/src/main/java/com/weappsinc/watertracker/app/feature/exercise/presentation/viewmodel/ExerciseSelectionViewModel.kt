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

class ExerciseSelectionViewModel(
    private val observeExerciseLevel: ObserveExerciseLevelUseCase,
    private val saveExerciseLevel: SaveExerciseLevelUseCase
) : ViewModel() {
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

    fun saveSelection() {
        viewModelScope.launch { saveExerciseLevel(_selectedLevel.value) }
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
