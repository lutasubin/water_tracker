package com.weappsinc.watertracker.app.feature.exercise.domain.repository

import com.weappsinc.watertracker.app.feature.exercise.domain.model.ExerciseLevel
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun observeExerciseLevel(): Flow<ExerciseLevel>
    suspend fun saveExerciseLevel(level: ExerciseLevel)
}

