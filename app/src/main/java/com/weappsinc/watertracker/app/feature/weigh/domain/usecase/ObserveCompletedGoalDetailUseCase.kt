package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoalDetail
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighCompletedGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ObserveCompletedGoalDetailUseCase(
    private val repository: WeighCompletedGoalRepository,
) {
    operator fun invoke(goalId: Long): Flow<WeighCompletedGoalDetail> = combine(
        repository.observeCompletedById(goalId),
        repository.observeCompletedLogsByGoalId(goalId),
    ) { goal, logs ->
        WeighCompletedGoalDetail(goal = goal, logs = logs)
    }
}
