package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import coil.ImageLoader
import com.weappsinc.watertracker.app.core.components.massUnitShortLabel
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighGoalHistoryRowUi

@Composable
internal fun WeighGoalHistoryListItem(
    row: WeighGoalHistoryRowUi,
    unit: MassUnit,
    imageLoader: ImageLoader,
    onClick: (Long) -> Unit,
) {
    Column {
        WeighGoalCard(
            targetValueText = row.targetValueText,
            massUnitLabel = massUnitShortLabel(unit),
            gapValueText = row.deltaValueText,
            journeyProgressFraction = 1f,
            journeyProgressPercent = 100,
            imageLoader = imageLoader,
            onClick = { onClick(row.id) },
        )
        Text(
            text = stringResource(
                R.string.weigh_goal_history_achieved_format,
                row.achievedValueText,
                massUnitShortLabel(unit),
                row.completedAtText,
            ),
            style = AppTypography.BodyMedium,
            color = AppColors.HomeMuted,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = com.weappsinc.watertracker.app.core.theme.WeighDimens.TargetSectionTopSpacing),
        )
    }
}
