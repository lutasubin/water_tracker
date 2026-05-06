package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Nút DRINK: viên thuốc, nền primary phẳng, + và chữ trắng căn giữa. */
@Composable
fun TrackerDrinkButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pill = RoundedCornerShape(AppDimens.HomeDrinkButtonHeight / 2)
    Button(
        onClick = onClick,
        modifier = modifier
            .width(AppDimens.HomeDrinkButtonWidth)
            .height(AppDimens.HomeDrinkButtonHeight),
        shape = pill,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.HomePrimary,
            contentColor = AppColors.HomeCard
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp
        ),
        contentPadding = ButtonDefaults.ContentPadding
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = AppColors.HomeCard,
                modifier = Modifier.size(AppDimens.HomeDrinkIconSize)
            )
            Spacer(Modifier.width(AppDimens.HomeDrinkContentGap))
            Text(
                text = stringResource(R.string.drink),
                color = AppColors.HomeCard,
                style = AppTypography.DrinkCta
            )
        }
    }
}
