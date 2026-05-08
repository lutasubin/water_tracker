package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import coil.ImageLoader
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Nội dung chính sheet Rate Us (bố cục mock). */
@Composable
fun RateUsSheetContent(
    selectedStars: Int,
    imageLoader: ImageLoader,
    onStarSelected: (Int) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val refs = remember(selectedStars) { RateUsTextRefs.forStars(selectedStars) }
    val enabled = selectedStars >= 1
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.RateUsHorizontalPadding)
            .padding(bottom = AppDimens.RateUsBottomPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RateUsHeroIllustration(
            selectedStars = selectedStars,
            imageLoader = imageLoader,
            onStarSelected = onStarSelected,
        )
        Spacer(Modifier.height(AppDimens.RateUsSectionSpacing))
        refs.titleRes?.let { res ->
            Text(
                text = stringResource(res),
                style = AppTypography.Title2,
                color = AppColors.HomeTitle,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(AppDimens.RateUsSectionSpacing))
        }
        Text(
            text = stringResource(refs.bodyRes),
            style = AppTypography.BodyLarge,
            color = AppColors.HomeTitle,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(AppDimens.RateUsSectionSpacing))
        Text(
            text = stringResource(R.string.rate_sheet_subtitle_vi),
            style = AppTypography.BodyMedium,
            color = AppColors.HomePrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(AppDimens.RateUsSectionSpacing))
        RateUsSheetPrimaryButton(enabled = enabled, onSubmit = onSubmit)
    }
}
