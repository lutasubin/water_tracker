package com.weappsinc.watertracker.app.feature.weigh.domain.model

data class ArchiveCompletedWeightGoalOutcome(
    val archivedGoalId: Long,
    val preferencesCleared: Boolean,
)
