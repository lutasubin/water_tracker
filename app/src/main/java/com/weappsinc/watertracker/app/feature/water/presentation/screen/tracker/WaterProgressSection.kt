package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.components.CapsuleProgressBar
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit
import com.weappsinc.watertracker.app.feature.water.domain.util.WaterAmountFormat

/** Khối số uống (căn giữa) + thanh tiến độ + nhãn / %. */
@Composable
fun WaterProgressSection(
    intakeMlToday: Int,
    displayUnit: WaterUnit,
    progressFraction: Float,
    progressPercent: Int,
    isGoalCompleted: Boolean,
    drawContainer: Boolean = true,
    modifier: Modifier = Modifier
) {
    val unitSuffix = if (displayUnit == WaterUnit.ML) stringResource(R.string.unit_ml) else stringResource(R.string.unit_l)
    val elapsedMs by rememberWaterProgressWaveElapsedMs()
    val contentHorizontalPadding = if (drawContainer) AppDimens.HomeHeroCardHorizontalPadding else 4.dp
    val sectionModifier = if (drawContainer) {
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppDimens.HomeHeroCardCorner))
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0f to AppColors.HomeHeroGradientTop,
                        0.42f to AppColors.HomeHeroGradientMid,
                        1f to AppColors.HomeHeroGradientBottom,
                    )
                )
            )
            .drawWithContent {
                drawWaterProgressWaveLayers(size = size, elapsedMs = elapsedMs)
                drawWaterProgressDepthOverlay(size = size)
                drawContent()
            }
    } else {
        modifier.fillMaxWidth()
    }
    Box(
        modifier = sectionModifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = contentHorizontalPadding,
                    vertical = AppDimens.HomeHeroCardVerticalPadding,
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = WaterAmountFormat.format(intakeMlToday, displayUnit),
                    color = Color.White,
                    style = AppTypography.WaterGoalValue
                )
                Spacer(Modifier.width(AppDimens.HomeHeroValueUnitGap))
                Text(
                    text = unitSuffix,
                    color = Color.White,
                    style = AppTypography.Title3
                )
            }
            Spacer(Modifier.height(AppDimens.HomeHeroValueBottomSpacing))
            CapsuleProgressBar(
                progressFraction = progressFraction,
                height = AppDimens.HomeHeroProgressHeight,
                trackColor = AppColors.HomeHeroProgressTrack,
                fillColor = AppColors.HomeHeroProgressFill,
            )
            Spacer(Modifier.height(AppDimens.HomeHeroBottomRowTopSpacing))
            RowBetweenLabels(
                progressPercent = progressPercent,
                isGoalCompleted = isGoalCompleted,
                contentColor = Color.White,
            )
        }
    }
}

@Composable
private fun RowBetweenLabels(
    progressPercent: Int,
    isGoalCompleted: Boolean,
    contentColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (isGoalCompleted) stringResource(R.string.today_goal_done_label) else stringResource(R.string.today_progress_label),
            color = contentColor,
            style = AppTypography.BodyMedium
        )
        Text(
            text = "$progressPercent%",
            color = contentColor,
            style = AppTypography.BodyMedium
        )
    }
}
