package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Nút CTA sheet Rate Us — disabled khi chưa chọn sao (mock nhạt). */
@Composable
fun RateUsSheetPrimaryButton(enabled: Boolean, onSubmit: () -> Unit) {
    Button(
        onClick = onSubmit,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimens.RateUsCtaHeight),
        shape = RoundedCornerShape(AppDimens.RateUsCtaHeight / 2),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.HomePrimary,
            disabledContainerColor = AppColors.RateUsCtaDisabledBg,
            contentColor = AppColors.GenderSelectedContent,
            disabledContentColor = AppColors.RateUsCtaDisabledContent,
        ),
    ) {
        Text(
            text = stringResource(R.string.me_menu_rate_us),
            style = AppTypography.Button,
        )
    }
}
