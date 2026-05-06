package com.weappsinc.watertracker.app.core.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit

/** Nhãn đơn vị khối lượng cho UI (Compose). */
@Composable
fun massUnitShortLabel(unit: MassUnit): String = stringResource(
    when (unit) {
        MassUnit.KG -> R.string.unit_mass_kg
        MassUnit.LB -> R.string.unit_mass_lb
    }
)
