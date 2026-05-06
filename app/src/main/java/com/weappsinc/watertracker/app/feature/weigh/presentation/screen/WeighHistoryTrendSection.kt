package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighHistoryChartPoint

@Composable
fun WeighHistoryTrendSection(
    chartPoints: List<WeighHistoryChartPoint>,
    xLabels: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(WeighDimens.WeighTrendCardCorner))
            .background(AppColors.HomeCard)
            .padding(AppDimens.HomeCardInnerPadding)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = AppText.WEIGH_HISTORY_TREND_TITLE,
                style = AppTypography.WeighHistoryTrendTitle,
                color = AppColors.HomeTitle
            )
            Text(
                text = AppText.WEIGH_HISTORY_LAST_7_DAYS,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(AppColors.WeighHistoryBadgeBg)
                    .padding(
                        horizontal = WeighDimens.WeighTrendBadgeHPad,
                        vertical = WeighDimens.WeighTrendBadgeVPad
                    ),
                style = AppTypography.BodyMedium,
                color = AppColors.WeighHistoryAccent
            )
        }
        Spacer(Modifier.height(14.dp))
        if (chartPoints.isEmpty()) {
            Text(
                text = AppText.WEIGH_HISTORY_EMPTY,
                style = AppTypography.BodyMedium,
                color = AppColors.HomeMuted
            )
        } else {
            WeighHistoryLineChart(chartPoints = chartPoints)
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth()) {
                xLabels.forEach { label ->
                    Text(
                        text = label,
                        modifier = Modifier.weight(1f),
                        style = AppTypography.BodyMedium,
                        color = AppColors.HomeMuted,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
