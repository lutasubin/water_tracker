package com.weappsinc.watertracker.app.feature.weigh.domain.model

data class WeighCompletedGoalDetail(
    val goal: WeighCompletedGoal?,
    val logs: List<WeighCompletedGoalLog>,
)
