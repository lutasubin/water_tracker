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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.ImageLoader
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Nội dung sheet Rate Us: đóng (end) → mascot → chữ → tagline → sao → CTA. */
@Composable
fun RateUsSheetContent(
    selectedStars: Int,
    imageLoader: ImageLoader,
    onStarSelected: (Int) -> Unit,
    onSubmit: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val refs = remember(selectedStars) { RateUsTextRefs.forStars(selectedStars) }
    val enabled = selectedStars >= 1
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = AppDimens.RateUsBottomPadding),
    ) {
        RateUsSheetCloseRow(
            onClose = onClose,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = AppDimens.RateUsHorizontalPadding)
                .padding(end = AppDimens.RateUsCloseTrailingInset),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimens.RateUsHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RateUsMascotHero(selectedStars = selectedStars, imageLoader = imageLoader)
            Spacer(Modifier.height(AppDimens.RateUsMascotToTextGap))
            refs.titleRes?.let { res ->
                Text(
                    text = stringResource(res),
                    style = AppTypography.Title3,
                    color = AppColors.HomeTitle,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(AppDimens.RateUsSectionSpacing))
            }
            Text(
                text = stringResource(refs.bodyRes),
                style = AppTypography.BodyLarge.copy(fontWeight = FontWeight.Normal),
                color = AppColors.HomeTitle,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(R.string.rate_sheet_subtitle),
                style = AppTypography.BodyMedium,
                color = AppColors.HomeSecondaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = AppDimens.RateUsSectionSpacing),
            )
            Spacer(Modifier.height(AppDimens.RateUsSectionSpacing))
            RateUsInteractiveStarRow(
                selectedStars = selectedStars,
                onStarSelected = onStarSelected,
            )
            Spacer(Modifier.height(AppDimens.RateUsSectionSpacing))
            RateUsSheetPrimaryButton(enabled = enabled, onSubmit = onSubmit)
        }
    }
}
