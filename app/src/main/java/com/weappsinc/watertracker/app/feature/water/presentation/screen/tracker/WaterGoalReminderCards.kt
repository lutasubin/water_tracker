package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader

/**
 * Chỉ thẻ mục tiêu nước (full width). Nhắc nhở tạm bỏ khỏi màn Water.
 */
@Composable
fun WaterGoalReminderCards(
    goalDisplayCompact: String,
    imageLoader: ImageLoader,
    onEditGoal: () -> Unit,
    modifier: Modifier = Modifier
) {
    GoalStatCard(
        goalDisplayCompact = goalDisplayCompact,
        imageLoader = imageLoader,
        onEditGoal = onEditGoal,
        modifier = modifier.fillMaxWidth()
    )
}
