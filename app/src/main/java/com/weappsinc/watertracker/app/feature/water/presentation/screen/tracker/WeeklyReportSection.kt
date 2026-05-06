package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.water.presentation.state.WeekDayRingUi

/** Card báo cáo tuần: một vòng xám + cung xanh; nhãn ngày chọn màu primary. */
@Composable
fun WeeklyReportSection(
    weekRings: List<WeekDayRingUi>,
    imageLoader: ImageLoader,
    onOpenReport: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.HomeCardCorner),
        color = AppColors.HomeCard,
        shadowElevation = AppDimens.HomeCardShadowElevation
    ) {
        Column(modifier = Modifier.padding(AppDimens.HomeCardInnerPadding)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = AssetPaths.REPORT_ICON,
                        contentDescription = null,
                        imageLoader = imageLoader,
                        modifier = Modifier.size(24.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(Modifier.size(8.dp))
                    Text(
                        text = AppText.REPORT_TITLE,
                        color = AppColors.HomeTitle,
                        style = AppTypography.Title3
                    )
                }
                Text(
                    text = AppText.REPORT_DETAILS,
                    color = AppColors.HomePrimary,
                    style = AppTypography.BodyMedium,
                    modifier = Modifier.clickable(onClick = onOpenReport)
                )
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                weekRings.forEach { day ->
                    WeekDayRingCell(
                        day = day,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun WeekDayRingCell(day: WeekDayRingUi, modifier: Modifier = Modifier) {
    val prog = if (day.beforeInstall) 0f else day.progress
    val labelColor = if (day.isToday) AppColors.HomePrimary else AppColors.HomeMuted
    val labelWeight = if (day.isToday) FontWeight.Bold else FontWeight.Medium
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            progress = { prog },
            modifier = Modifier.size(AppDimens.HomeWeekRingSize),
            color = AppColors.HomePrimary,
            trackColor = AppColors.HomeProgressTrack,
            strokeWidth = AppDimens.HomeWeekRingStrokeWidth,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = day.label,
            color = labelColor,
            style = AppTypography.BodyMedium,
            fontWeight = labelWeight,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
