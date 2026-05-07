package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay

/** Ký hiệu trừ (Unicode) để đồng bộ nút − với mock. */
private const val STEP_GLYPH_MINUS = "\u2212"

/**
 * Hàng chọn cân mục tiêu: nút − / số + đơn vị nhỏ cạnh số / nút +
 * (thiết kế mock: ô xanh nhạt, glyph xanh đậm, typography lớn).
 */
@Composable
internal fun WeighTargetWeightPickerRow(
    draftKg: Float,
    massUnit: MassUnit,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val unitLabel =
        if (massUnit == MassUnit.KG) stringResource(R.string.unit_mass_kg) else stringResource(R.string.unit_mass_lb)
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        WeightTargetPickerSquare(symbol = STEP_GLYPH_MINUS, onClick = onDecrement)
        Row(
            Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = MassDisplay.formatTargetKg(draftKg, massUnit),
                style = AppTypography.WeighTargetSheetValue,
                color = AppColors.HomeTitle,
            )
            Text(
                text = unitLabel,
                modifier = Modifier.padding(start = WeighDimens.SheetWeightUnitGap),
                style = AppTypography.WeighTargetSheetUnit,
                color = AppColors.HomeMuted,
            )
        }
        WeightTargetPickerSquare(symbol = "+", onClick = onIncrement)
    }
}

@Composable
private fun WeightTargetPickerSquare(symbol: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(WeighDimens.SheetStepperButtonSize)
            .clip(RoundedCornerShape(WeighDimens.SheetStepperCorner))
            .background(AppColors.WeighSheetStepperBg)
            .clickable(onClick = onClick),
        Alignment.Center,
    ) {
        Text(symbol, style = AppTypography.WeighTargetSheetStepperGlyph, color = AppColors.WeighSheetStepperIcon)
    }
}
