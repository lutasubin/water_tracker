package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit

@Composable
fun WaterTrackerHeroSection(
    streakDays: Int,
    intakeMlToday: Int,
    displayUnit: WaterUnit,
    progressFraction: Float,
    progressPercent: Int,
    isGoalCompleted: Boolean,
    modifier: Modifier = Modifier,
) {
    val elapsedMs by rememberWaterProgressWaveElapsedMs()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0f to AppColors.HomeHeroGradientTop,
                        0.42f to AppColors.HomeHeroGradientMid,
                        1f to AppColors.HomeHeroGradientBottom,
                    ),
                ),
            )
            .drawWithContent {
                drawWaterProgressWaveLayers(size = size, elapsedMs = elapsedMs)
                drawWaterProgressDepthOverlay(size = size)
                drawContent()
            }
            .padding(
                horizontal = AppDimens.HomeHeroCardHorizontalPadding,
                vertical = AppDimens.HomeHeroCardVerticalPadding,
            ),
    ) {
        WaterTrackerHeader(
            streakDays = streakDays,
            titleColor = Color.White,
            streakContainerColor = Color.White.copy(alpha = 0.18f),
            streakTextColor = Color.White,
        )
        Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
        WaterProgressSection(
            intakeMlToday = intakeMlToday,
            displayUnit = displayUnit,
            progressFraction = progressFraction,
            progressPercent = progressPercent,
            isGoalCompleted = isGoalCompleted,
            drawContainer = false,
        )
    }
}
