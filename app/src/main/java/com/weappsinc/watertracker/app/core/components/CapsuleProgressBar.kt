package com.weappsinc.watertracker.app.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens

/**
 * Thanh capsule một lớp (track + fill cùng clip) — dùng Water / Splash, tránh hai lớp của
 * LinearProgressIndicator.
 */
@Composable
fun CapsuleProgressBar(
    progressFraction: Float,
    modifier: Modifier = Modifier,
    height: Dp = AppDimens.HomeProgressHeight,
    trackColor: Color = AppColors.HomeWaterProgressBarTrack,
    fillColor: Color = AppColors.HomePrimary,
) {
    val f = progressFraction.coerceIn(0f, 1f)
    val shape = RoundedCornerShape(height / 2)
    Box(
        modifier
            .fillMaxWidth()
            .height(height)
            .clip(shape)
            .background(trackColor)
    ) {
        if (f > 0f) {
            Box(
                Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxHeight()
                    .fillMaxWidth(f)
                    .background(fillColor)
            )
        }
    }
}
