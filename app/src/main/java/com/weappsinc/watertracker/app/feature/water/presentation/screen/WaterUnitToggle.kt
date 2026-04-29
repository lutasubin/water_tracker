package com.weappsinc.watertracker.app.feature.water.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

@Composable
internal fun WaterUnitToggle(
    leftText: String,
    rightText: String,
    selectedLeft: Boolean,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = AppColors.AdjustButton
    val selectedBg = AppColors.GenderScreenBackground
    val selectedText = AppColors.AdjustButton
    val unselectedBg = androidx.compose.ui.graphics.Color.Transparent
    val unselectedText = AppColors.AdjustButton

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(26.dp))
            .border(1.dp, borderColor, RoundedCornerShape(26.dp))
            .padding(2.dp)
            .height(AppDimens.WaterGoalUnitToggleHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UnitCell(
            text = leftText,
            selected = selectedLeft,
            onClick = onLeftClick,
            selectedBg = selectedBg,
            selectedText = selectedText,
            unselectedBg = unselectedBg,
            unselectedText = unselectedText
        )
        Spacer(Modifier.width(6.dp))
        UnitCell(
            text = rightText,
            selected = !selectedLeft,
            onClick = onRightClick,
            selectedBg = selectedBg,
            selectedText = selectedText,
            unselectedBg = unselectedBg,
            unselectedText = unselectedText
        )
    }
}

@Composable
private fun UnitCell(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    selectedBg: androidx.compose.ui.graphics.Color,
    selectedText: androidx.compose.ui.graphics.Color,
    unselectedBg: androidx.compose.ui.graphics.Color,
    unselectedText: androidx.compose.ui.graphics.Color
) {
    val bg = if (selected) selectedBg else unselectedBg
    val fg = if (selected) selectedText else unselectedText
    Text(
        text = text,
        color = fg,
        style = AppTypography.Button,
        modifier = Modifier
            .clip(RoundedCornerShape(22.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 26.dp, vertical = 10.dp)
    )
}

