package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay

private const val STEP_KG = 0.5f

@Composable
fun WeighGoalDetailTodayCard(
    displayDraftKg: Float,
    massUnit: MassUnit,
    massUnitLabel: String,
    showWeighRecordCta: Boolean,
    savedBannerTime: String?,
    recordError: Boolean,
    onStepKg: (Float) -> Unit,
    onRecord: () -> Unit,
    onHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .background(AppColors.HomeCard)
            .padding(AppDimens.HomeCardInnerPadding)
    ) {
        WeighTodayCardHeaderRow(onHistoryClick = onHistoryClick)
        Spacer(Modifier.height(18.dp))
        WeighTodayWeightStepperRow(
            weightText = MassDisplay.formatTargetKg(displayDraftKg, massUnit),
            massUnitLabel = massUnitLabel,
            onStepMinus = { onStepKg(-STEP_KG) },
            onStepPlus = { onStepKg(STEP_KG) }
        )
        Spacer(Modifier.height(16.dp))
        if (showWeighRecordCta) {
            WeighTodayRecordPillButton(onClick = onRecord)
            if (recordError) {
                Text(
                    text = stringResource(R.string.weigh_goal_detail_record_error),
                    modifier = Modifier.padding(top = 8.dp),
                    style = AppTypography.BodyMedium,
                    color = AppColors.WeighProgressDeltaUnfavorable
                )
            }
        } else if (savedBannerTime != null) {
            WeighTodaySavedBanner(timeText = savedBannerTime)
        }
    }
}
