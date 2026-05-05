package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.constants.AppText
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
    modifier: Modifier = Modifier
) {
    val unitSuffix = if (displayUnit == WaterUnit.ML) AppText.UNIT_ML else AppText.UNIT_L
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = WaterAmountFormat.format(intakeMlToday, displayUnit),
                color = AppColors.HomeTitle,
                style = AppTypography.WaterGoalValue
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = unitSuffix,
                color = AppColors.HomeTitle,
                style = AppTypography.Title3
            )
        }
        Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
        LinearProgressIndicator(
            progress = { progressFraction },
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimens.HomeProgressHeight)
                .clip(RoundedCornerShape(AppDimens.HomeProgressHeight / 2)),
            color = AppColors.HomePrimary,
            trackColor = AppColors.HomeProgressTrack,
            drawStopIndicator = {}
        )
        Spacer(Modifier.height(AppDimens.WaterTrackerProgressToLabelSpacing))
        RowBetweenLabels(progressPercent)
    }
}

@Composable
private fun RowBetweenLabels(progressPercent: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AppText.TODAY_PROGRESS_LABEL,
            color = AppColors.HomeTitle,
            style = AppTypography.BodyMedium
        )
        Text(
            text = "$progressPercent%",
            color = AppColors.HomeProgressPercentText,
            style = AppTypography.BodyMedium
        )
    }
}
