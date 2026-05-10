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
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import com.weappsinc.watertracker.app.feature.water.presentation.state.MeProfileUiState

/** Khối Chung: chiều cao / cân / tuổi / giới tính — bấm để chỉnh (giống màn Personal Data). */
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
    val heightRight = meProfileHeightRightText(state)
    val weightRight = meProfileWeightRightText(state)
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
            iconTint = AppColors.HomePrimary,
        )
        MeProfileNavRow(
            iconPath = AssetPaths.WEIGHT_ICON,
            label = stringResource(R.string.weigh_weight_label),
            valueText = weightRight,
            imageLoader = imageLoader,
            onClick = onEditWeight,
            iconTint = AppColors.HomePrimary,
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
