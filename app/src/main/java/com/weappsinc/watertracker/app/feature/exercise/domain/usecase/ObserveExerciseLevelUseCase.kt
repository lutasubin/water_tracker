package com.weappsinc.watertracker.app.feature.exercise.domain.usecase

import com.weappsinc.watertracker.app.feature.exercise.domain.repository.ExerciseRepository

class ObserveExerciseLevelUseCase(private val repository: ExerciseRepository) {
    operator fun invoke() = repository.observeExerciseLevel()
}

