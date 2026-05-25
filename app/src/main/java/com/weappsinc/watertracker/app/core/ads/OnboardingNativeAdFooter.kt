package com.weappsinc.watertracker.app.core.ads

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.weappsinc.watertracker.app.core.components.AppPrimaryButton
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens

@Composable
fun OnboardingNativeAdFooter(
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonContainerColor: Color = AppColors.GenderPrimary,
    buttonTextColor: Color = AppColors.GenderSelectedContent,
    bottomPadding: Dp = AppDimens.AgeButtonBottomPadding,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = bottomPadding),
    ) {
        AppPrimaryButton(
            text = buttonText,
            onClick = onButtonClick,
            containerColor = buttonContainerColor,
            textColor = buttonTextColor,
        )
        Spacer(Modifier.height(AppDimens.HomeSectionSpacing))
        AppNativeAd(placement = NativePlacement.Onboarding)
    }
}
