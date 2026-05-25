package com.weappsinc.watertracker.app.core.ads

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.theme.AppColors

@Composable
fun AdsShimmerBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
) {
    val transition = rememberInfiniteTransition(label = "ads_shimmer")
    val translateX = transition.animateFloat(
        initialValue = -300f,
        targetValue = 900f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "ads_shimmer_translate",
    )
    val brush = Brush.linearGradient(
        colors = listOf(
            AppColors.HomeProgressTrack,
            AppColors.HomeCard,
            AppColors.HomeProgressTrack,
        ),
        start = Offset(translateX.value, 0f),
        end = Offset(translateX.value + 260f, 260f),
    )
    Box(
        modifier = modifier
            .clip(shape)
            .background(brush),
    )
}
