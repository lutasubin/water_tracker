package com.weappsinc.watertracker.app.feature.water.presentation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportMonthPagerUi

/** Nút tròn nền xanh nhạt + track mảnh + thumb dày bo tròn (pager 7 ngày tháng). */
@Composable
fun ReportChartMonthFooter(
    pager: ReportMonthPagerUi,
    onOlder: () -> Unit,
    onNewer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(AppDimens.ReportMonthFooterHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MonthPagerCircleButton(onOlder, pager.canGoOlder, toLeft = true)
        BoxWithConstraints(
            Modifier
                .weight(1f)
                .height(AppDimens.ReportMonthScrollTrackHeight)
        ) {
            val trackH = AppDimens.ReportMonthScrollTrackLineHeight
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(trackH)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(trackH / 2))
                    .background(AppColors.ReportPagerTrack)
            )
            val thumbH = AppDimens.ReportMonthScrollThumbHeight
            val pill = RoundedCornerShape(thumbH / 2)
            if (pager.maxWindowIndex == 0) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(thumbH)
                        .align(Alignment.Center)
                        .clip(pill)
                        .background(AppColors.HomePrimary)
                )
            } else {
                val thumbW = maxWidth * pager.thumbWidthFraction
                val w = thumbW.coerceAtLeast(AppDimens.ReportMonthScrollThumbMinWidth).coerceAtMost(maxWidth)
                val thumbStart = maxWidth * pager.thumbStartFraction
                val startClamped = thumbStart.coerceAtMost((maxWidth - w).coerceAtLeast(0.dp))
                Box(
                    Modifier
                        .padding(start = startClamped)
                        .width(w)
                        .height(thumbH)
                        .align(Alignment.CenterStart)
                        .clip(pill)
                        .background(AppColors.HomePrimary)
                )
            }
        }
        MonthPagerCircleButton(onNewer, pager.canGoNewer, toLeft = false)
    }
}

@Composable
private fun MonthPagerCircleButton(onClick: () -> Unit, enabled: Boolean, toLeft: Boolean) {
    Surface(
        shape = CircleShape,
        color = AppColors.ReportPagerButtonBackground,
        shadowElevation = 0.dp,
        modifier = Modifier.size(AppDimens.ReportMonthFooterCircleSize)
    ) {
        IconButton(onClick = onClick, enabled = enabled) {
            Icon(
                imageVector =
                    if (toLeft) Icons.AutoMirrored.Filled.KeyboardArrowLeft
                    else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = if (enabled) AppColors.HomePrimary else AppColors.HomeMuted,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
