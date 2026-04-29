package com.weappsinc.watertracker.app.feature.exercise.domain.usecase

import com.weappsinc.watertracker.app.feature.exercise.domain.model.ExerciseLevel
import com.weappsinc.watertracker.app.feature.exercise.domain.repository.ExerciseRepository

class SaveExerciseLevelUseCase(private val repository: ExerciseRepository) {
    suspend operator fun invoke(level: ExerciseLevel) {
        repository.saveExerciseLevel(level)
    }
}

