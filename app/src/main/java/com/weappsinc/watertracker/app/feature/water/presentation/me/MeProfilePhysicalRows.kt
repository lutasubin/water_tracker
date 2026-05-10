package com.weappsinc.watertracker.app.feature.water.presentation.me

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import com.weappsinc.watertracker.app.feature.water.presentation.state.MeProfileUiState
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit

/** Bốn hàng Chiều cao / Cân nặng / Tuổi / Giới tính (tab Me). */
@Composable
fun MeProfilePhysicalRows(
    state: MeProfileUiState,
    imageLoader: ImageLoader,
    onEditTall: () -> Unit,
    onEditWeight: () -> Unit,
    onEditAge: () -> Unit,
    onEditGender: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val heightRight =
        if (state.heightValueText == "--") {
            "--"
        } else {
            "${state.heightValueText} ${stringResource(R.string.unit_cm)}"
        }
    val massLabel =
        if (state.displayMassUnit == MassUnit.KG) stringResource(R.string.unit_mass_kg)
        else stringResource(R.string.unit_mass_lb)
    val weightRight =
        if (state.weightValueText == "--") "--"
        else "${state.weightValueText} $massLabel"
    val sexLabel = when (state.sex) {
        GenderType.MALE -> stringResource(R.string.male)
        GenderType.FEMALE -> stringResource(R.string.female)
        GenderType.OTHER -> stringResource(R.string.other_gender)
    }
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppDimens.MeProfileMenuCardSpacing),
    ) {
        MeProfileNavRow(
            iconPath = AssetPaths.HEIGHT_ICON,
            label = stringResource(R.string.weigh_height_label),
            valueText = heightRight,
            imageLoader = imageLoader,
            onClick = onEditTall,
        )
        MeProfileNavRow(
            iconPath = AssetPaths.WEIGHT_ICON,
            label = stringResource(R.string.weigh_weight_label),
            valueText = weightRight,
            imageLoader = imageLoader,
            onClick = onEditWeight,
        )
        MeProfileNavRow(
            iconPath = AssetPaths.AGE_ICON,
            label = stringResource(R.string.me_field_age),
            valueText = state.ageValueText,
            imageLoader = imageLoader,
            onClick = onEditAge,
        )
        MeProfileNavRow(
            iconPath = AssetPaths.SEX_ICON,
            label = stringResource(R.string.me_field_sex),
            valueText = sexLabel,
            imageLoader = imageLoader,
            onClick = onEditGender,
        )
    }
}
