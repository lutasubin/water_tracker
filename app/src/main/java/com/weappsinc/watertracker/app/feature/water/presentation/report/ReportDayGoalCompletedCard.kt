package com.weappsinc.watertracker.app.feature.water.presentation.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Thẻ báo ngày đã đạt/vượt mục tiêu nước (100%) trên màn Report tab Ngày. */
@Composable
fun ReportDayGoalCompletedCard(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.ReportHorizontalPadding),
        shape = RoundedCornerShape(AppDimens.HomeCardCorner),
        color = AppColors.HomeCard,
        shadowElevation = AppDimens.HomeCardShadowElevation,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(AppDimens.HomeCardInnerPadding),
        ) {
            Text(
                text = stringResource(R.string.report_day_goal_completed_title),
                color = AppColors.HomeTitle,
                style = AppTypography.Title3,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.report_day_goal_completed_percent),
                color = AppColors.HomePrimary,
                style = AppTypography.DisplayNumber,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}
