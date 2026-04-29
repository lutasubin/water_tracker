package com.weappsinc.watertracker.app.feature.exercise.data.repository

import com.weappsinc.watertracker.app.core.local.GenderSQLiteHelper
import com.weappsinc.watertracker.app.feature.exercise.domain.model.ExerciseLevel
import com.weappsinc.watertracker.app.feature.exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExerciseRepositoryImpl(
    private val helper: GenderSQLiteHelper
) : ExerciseRepository {
    private val selectedLevel = MutableStateFlow(
        helper.getExerciseLevel()
            ?.let { runCatching { ExerciseLevel.valueOf(it) }.getOrNull() }
            ?: ExerciseLevel.LOW
    )

    override fun observeExerciseLevel(): Flow<ExerciseLevel> = selectedLevel.asStateFlow()

    override suspend fun saveExerciseLevel(level: ExerciseLevel) {
        helper.saveExerciseLevel(level.name)
        selectedLevel.value = level
    }
}

