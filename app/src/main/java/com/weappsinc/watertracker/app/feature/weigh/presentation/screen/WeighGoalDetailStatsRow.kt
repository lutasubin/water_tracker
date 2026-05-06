package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingDown
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors

/** Hai thẻ Ngày bắt đầu và Tiến triển (icon + nhãn + số + đơn vị theo mock chi tiết). */
@Composable
fun WeighGoalDetailStatsRow(
    startWeightText: String,
    massUnitLabel: String,
    progressDeltaValueText: String,
    progressDeltaFavorable: Boolean,
    progressDeltaNeutral: Boolean,
    modifier: Modifier = Modifier
) {
    val deltaColor = when {
        progressDeltaNeutral -> AppColors.HomeTitle
        progressDeltaFavorable -> AppColors.WeighProgressDeltaFavorable
        else -> AppColors.WeighProgressDeltaUnfavorable
    }
    val (deltaIcon, deltaIconTint) = when {
        progressDeltaNeutral ->
            Icons.AutoMirrored.Outlined.TrendingUp to AppColors.HomeMuted
        progressDeltaFavorable ->
            Icons.AutoMirrored.Outlined.TrendingUp to AppColors.WeighProgressDeltaFavorable
        else ->
            Icons.AutoMirrored.Outlined.TrendingDown to AppColors.WeighProgressDeltaUnfavorable
    }
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        WeighGoalDetailStatCard(
            icon = Icons.Outlined.Flag,
            iconTint = AppColors.WeighHistoryAccent,
            label = AppText.WEIGH_GOAL_DETAIL_START_DAY,
            valueText = startWeightText,
            valueColor = AppColors.HomeTitle,
            massUnitLabel = massUnitLabel,
            modifier = Modifier.weight(1f),
        )
        WeighGoalDetailStatCard(
            icon = deltaIcon,
            iconTint = deltaIconTint,
            label = AppText.WEIGH_GOAL_DETAIL_PROGRESS,
            valueText = progressDeltaValueText,
            valueColor = deltaColor,
            massUnitLabel = massUnitLabel,
            modifier = Modifier.weight(1f),
        )
    }
}
