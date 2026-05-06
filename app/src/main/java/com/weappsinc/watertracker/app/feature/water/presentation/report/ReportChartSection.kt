package com.weappsinc.watertracker.app.feature.water.presentation.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportPeriod
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportUiState

@Composable
fun ReportChartSection(
    state: ReportUiState,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onBarClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.ReportHorizontalPadding),
        shape = RoundedCornerShape(AppDimens.HomeCardCorner),
        color = AppColors.HomeCard,
        shadowElevation = AppDimens.HomeCardShadowElevation
    ) {
        Column(Modifier.padding(AppDimens.HomeCardInnerPadding)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPrev) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, null, tint = AppColors.HomeMuted)
                }
                Text(
                    text = state.anchorDateLabel,
                    style = AppTypography.Title3,
                    color = AppColors.HomeTitle,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
                IconButton(onClick = onNext) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = AppColors.HomeMuted)
                }
            }
            Spacer(Modifier.height(12.dp))
            ReportBarChartCanvas(
                bars = state.chartBars,
                chartMaxMl = state.chartMaxMl,
                selectedIndex = state.selectedBarIndex,
                onBarClick = onBarClick,
                isMonthScroll = false,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.period == ReportPeriod.Month && state.monthPager != null) {
                Spacer(Modifier.height(14.dp))
                ReportChartMonthFooter(
                    pager = state.monthPager,
                    onOlder = onPrev,
                    onNewer = onNext
                )
            }
        }
    }
}
