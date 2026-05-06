package com.weappsinc.watertracker.app.feature.water.presentation.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportUiState

@Composable
fun ReportSummaryCards(state: ReportUiState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.ReportHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryCard(
            label = state.summaryLeftLabel,
            valueMl = state.summaryLeftValueMl,
            valuePrimary = state.summaryLeftHighlightPrimary,
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            label = state.summaryRightLabel,
            valueMl = state.summaryRightValueMl,
            valuePrimary = false,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryCard(
    label: String,
    valueMl: Int,
    valuePrimary: Boolean,
    modifier: Modifier = Modifier
) {
    val num = ReportAmountFormat.formatMl(valueMl)
    val valueColor = if (valuePrimary) AppColors.HomePrimary else AppColors.HomeTitle
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimens.HomeCardCorner),
        color = AppColors.HomeCard,
        shadowElevation = AppDimens.HomeCardShadowElevation
    ) {
        Column(Modifier.padding(AppDimens.HomeCardInnerPadding)) {
            Text(text = label, color = AppColors.HomeMuted, style = AppTypography.BodyMedium)
            Spacer(Modifier.height(6.dp))
            Row {
                Text(
                    text = num,
                    color = valueColor,
                    style = AppTypography.DisplayNumber,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = " ${AppText.UNIT_ML}",
                    color = if (valuePrimary) AppColors.HomeSecondaryText else AppColors.HomeMuted,
                    style = AppTypography.Title3
                )
            }
        }
    }
}
