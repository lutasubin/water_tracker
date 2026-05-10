package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import coil.ImageLoader
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit

@Composable
fun WeighHeightWeightCards(
    heightValue: String,
    weightValue: String,
    massUnit: MassUnit,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    val massLabel = if (massUnit == MassUnit.KG) stringResource(R.string.unit_mass_kg) else stringResource(R.string.unit_mass_lb)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = WeighDimens.ScreenHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(WeighDimens.CardsSpacing)
    ) {
        WeighStatCard(
            iconPath = AssetPaths.HEIGHT_ICON,
            label = stringResource(R.string.weigh_height_label),
            primary = heightValue,
            unit = stringResource(R.string.unit_cm),
            imageLoader = imageLoader,
            modifier = Modifier.weight(1f)
        )
        WeighStatCard(
            iconPath = AssetPaths.WEIGHT_ICON,
            label = stringResource(R.string.weigh_weight_label),
            primary = weightValue,
            unit = massLabel,
            imageLoader = imageLoader,
            modifier = Modifier.weight(1f)
        )
    }
}
