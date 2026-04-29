package com.weappsinc.watertracker.app.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

@Composable
fun AppPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = AppColors.GenderPrimary,
    textColor: Color = AppColors.GenderSelectedContent
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(AppDimens.GenderButtonCorner),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        modifier = modifier.fillMaxWidth().height(AppDimens.AgeButtonHeight)
    ) {
        Text(
            text = text,
            color = textColor,
            style = AppTypography.Button
        )
    }
}
