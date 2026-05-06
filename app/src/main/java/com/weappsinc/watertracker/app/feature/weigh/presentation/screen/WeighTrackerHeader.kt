package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.components.AppUnitToggle
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit

@Composable
fun WeighTrackerHeader(
    massUnit: MassUnit,
    onMassUnitSelected: (MassUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = WeighDimens.ScreenHorizontalPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.weigh_tracker_title),
            color = AppColors.HomeTitle,
            style = AppTypography.Title1
        )
        AppUnitToggle(
            leftText = stringResource(R.string.unit_mass_kg),
            rightText = stringResource(R.string.unit_mass_lb),
            isLeftSelected = massUnit == MassUnit.KG,
            onLeftClick = { onMassUnitSelected(MassUnit.KG) },
            onRightClick = { onMassUnitSelected(MassUnit.LB) }
        )
    }
}
