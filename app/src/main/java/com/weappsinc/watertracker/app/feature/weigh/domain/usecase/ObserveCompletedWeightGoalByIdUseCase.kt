package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoal
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighCompletedGoalRepository
import kotlinx.coroutines.flow.Flow

class ObserveCompletedWeightGoalByIdUseCase(
    private val repository: WeighCompletedGoalRepository,
) {
    operator fun invoke(goalId: Long): Flow<WeighCompletedGoal?> =
        repository.observeCompletedById(goalId)
}
