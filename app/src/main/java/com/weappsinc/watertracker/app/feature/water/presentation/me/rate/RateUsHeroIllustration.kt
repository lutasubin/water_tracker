package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppDimens

/** Minh họa nhô lên mép sheet (offset âm). */
@Composable
fun RateUsHeroIllustration(
    selectedStars: Int,
    imageLoader: ImageLoader,
    onStarSelected: (Int) -> Unit,
) {
    Box(Modifier.offset(y = -AppDimens.RateUsHeroOverlapUp)) {
        RateUsIllustrationWithStars(
            assetUri = AssetPaths.rateSheetSvg(selectedStars),
            imageLoader = imageLoader,
            onStarSelected = onStarSelected,
        )
    }
}
