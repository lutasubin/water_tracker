package com.weappsinc.watertracker.app.feature.water.presentation.me

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.feature.water.presentation.state.MeProfileUiState
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit

/** Chuỗi hiển thị chiều cao / cân (dùng chung Hồ sơ + menu Cài đặt). */
@Composable
internal fun meProfileHeightRightText(state: MeProfileUiState): String =
    if (state.heightValueText == "--") {
        "--"
    } else {
        "${state.heightValueText} ${stringResource(R.string.unit_cm)}"
    }

@Composable
internal fun meProfileWeightRightText(state: MeProfileUiState): String {
    val massLabel =
        if (state.displayMassUnit == MassUnit.KG) stringResource(R.string.unit_mass_kg)
        else stringResource(R.string.unit_mass_lb)
    return if (state.weightValueText == "--") "--"
    else "${state.weightValueText} $massLabel"
}
