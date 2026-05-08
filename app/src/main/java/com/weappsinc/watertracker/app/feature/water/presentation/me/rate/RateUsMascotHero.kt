package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppDimens

/** Minh họa SVG rate_0…rate_5 theo số sao; thu nhỏ, căn giữa, nhô lên mép sheet. */
@Composable
fun RateUsMascotHero(
    selectedStars: Int,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .offset(y = -AppDimens.RateUsHeroOverlapUp)
            .fillMaxWidth()
            .height(AppDimens.RateUsMascotSlotHeight),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = AssetPaths.rateSheetSvg(selectedStars),
            contentDescription = null,
            imageLoader = imageLoader,
            modifier = Modifier
                .fillMaxWidth(AppDimens.RateUsMascotWidthFraction)
                .fillMaxHeight(),
            contentScale = ContentScale.Fit,
        )
    }
}
