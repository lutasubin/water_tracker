package com.weappsinc.watertracker.app.feature.water.data.repository

import com.weappsinc.watertracker.app.feature.age.domain.repository.AgeRepository
import com.weappsinc.watertracker.app.feature.exercise.domain.model.ExerciseLevel
import com.weappsinc.watertracker.app.feature.exercise.domain.repository.ExerciseRepository
import com.weappsinc.watertracker.app.feature.tall.domain.repository.TallRepository
import com.weappsinc.watertracker.app.feature.water.domain.WaterGoalCalculator
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterGoalRepository
import com.weappsinc.watertracker.app.feature.weight.domain.repository.WeightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class WaterGoalRepositoryImpl(
    private val ageRepository: AgeRepository,
    private val tallRepository: TallRepository,
    private val weightRepository: WeightRepository,
    private val exerciseRepository: ExerciseRepository
) : WaterGoalRepository {

    override fun observeDailyGoalMl(): Flow<Int> {
        return combine(
            ageRepository.observeAge(),
            tallRepository.observeTall(),
            weightRepository.observeWeight(),
            exerciseRepository.observeExerciseLevel()
        ) { age, height, weight, exerciseLevel ->
            WaterGoalCalculator.calculateDailyGoalMl(
                ageYears = age,
                heightCm = height,
                weightKg = weight,
                exerciseLevel = exerciseLevel
            )
        }
    }
}

