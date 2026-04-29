package com.weappsinc.watertracker.app.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

@Composable
fun AppUnitToggle(
    leftText: String,
    rightText: String,
    isLeftSelected: Boolean,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(AppDimens.AgeHighlightCorner))
            .border(1.dp, AppColors.GenderUnselectedBackground, RoundedCornerShape(AppDimens.AgeHighlightCorner))
            .padding(2.dp)
    ) {
        UnitChip(text = leftText, selected = isLeftSelected, onClick = onLeftClick)
        Spacer(Modifier.width(4.dp))
        UnitChip(text = rightText, selected = !isLeftSelected, onClick = onRightClick)
    }
}

@Composable
private fun UnitChip(text: String, selected: Boolean, onClick: () -> Unit) {
    val bg = if (selected) AppColors.GenderPrimary else AppColors.GenderScreenBackground
    val fg = if (selected) AppColors.GenderSelectedContent else AppColors.GenderUnselectedContent
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = fg, style = AppTypography.Button)
    }
}
