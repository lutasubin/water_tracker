package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.ImageLoader

/** Hàng hai thẻ thống kê: [GoalStatCard] + [ReminderStatCard]. */
@Composable
fun WaterGoalReminderCards(
    goalDisplayCompact: String,
    imageLoader: ImageLoader,
    onEditGoal: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GoalStatCard(
            goalDisplayCompact = goalDisplayCompact,
            imageLoader = imageLoader,
            onEditGoal = onEditGoal,
            modifier = Modifier.weight(1f)
        )
        ReminderStatCard(
            imageLoader = imageLoader,
            modifier = Modifier.weight(1f)
        )
    }
}
