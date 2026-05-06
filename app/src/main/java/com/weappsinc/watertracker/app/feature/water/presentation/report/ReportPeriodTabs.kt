package com.weappsinc.watertracker.app.feature.water.presentation.report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportPeriod

@Composable
fun ReportPeriodTabs(
    selected: ReportPeriod,
    onSelect: (ReportPeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.ReportHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PeriodChip(AppText.REPORT_TAB_DAY, selected == ReportPeriod.Day, Modifier.weight(1f)) {
            onSelect(ReportPeriod.Day)
        }
        PeriodChip(AppText.REPORT_TAB_WEEK, selected == ReportPeriod.Week, Modifier.weight(1f)) {
            onSelect(ReportPeriod.Week)
        }
        PeriodChip(AppText.REPORT_TAB_MONTH, selected == ReportPeriod.Month, Modifier.weight(1f)) {
            onSelect(ReportPeriod.Month)
        }
    }
}

@Composable
private fun PeriodChip(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val bg = if (selected) AppColors.HomePrimary else AppColors.HomeCard
    val fg = if (selected) AppColors.HomeCard else AppColors.HomeMuted
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .clickable(onClick = onClick),
        color = bg,
        shadowElevation = 0.dp
    ) {
        Text(
            text = label,
            color = fg,
            style = AppTypography.BodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center
        )
    }
}
