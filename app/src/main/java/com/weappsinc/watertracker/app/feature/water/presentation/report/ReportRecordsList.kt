package com.weappsinc.watertracker.app.feature.water.presentation.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportRecordUi

@Composable
fun ReportRecordsList(
    records: List<ReportRecordUi>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = AppDimens.ReportHorizontalPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.report_record_header),
            color = AppColors.HomeTitle,
            style = AppTypography.Title3
        )
        for (row in records) {
            Surface(
                shape = RoundedCornerShape(AppDimens.HomeCardCorner),
                color = AppColors.HomeCard,
                shadowElevation = AppDimens.HomeCardShadowElevation
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(AppDimens.HomeCardInnerPadding),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(row.timeLabel, color = AppColors.HomeMuted, style = AppTypography.BodyMedium)
                    Row {
                        Text(
                            ReportAmountFormat.formatMl(row.amountMl),
                            color = AppColors.HomePrimary,
                            fontWeight = FontWeight.Bold,
                            style = AppTypography.BodyLarge
                        )
                        Text(" ${stringResource(R.string.unit_ml)}", color = AppColors.HomeSecondaryText, style = AppTypography.BodyMedium)
                    }
                }
            }
        }
    }
}
