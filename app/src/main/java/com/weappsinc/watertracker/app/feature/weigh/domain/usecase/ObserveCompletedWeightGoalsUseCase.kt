package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoal
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighCompletedGoalRepository
import kotlinx.coroutines.flow.Flow

class ObserveCompletedWeightGoalsUseCase(
    private val repository: WeighCompletedGoalRepository
) {
    operator fun invoke(): Flow<List<WeighCompletedGoal>> = repository.observeAllCompletedDesc()
}
