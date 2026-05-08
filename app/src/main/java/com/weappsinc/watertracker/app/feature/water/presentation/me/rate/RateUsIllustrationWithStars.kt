package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppDimens

/** Minh họa SVG theo số sao + vùng chạm 5 ô (SVG đã vẽ sao). */
@Composable
fun RateUsIllustrationWithStars(
    assetUri: String,
    imageLoader: ImageLoader,
    onStarSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cdStarRow = stringResource(R.string.cd_rate_star_row)
    Box(modifier.fillMaxWidth()) {
        AsyncImage(
            model = assetUri,
            contentDescription = null,
            imageLoader = imageLoader,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentScale = ContentScale.FillWidth,
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(AppDimens.RateUsStarTouchStripHeight)
                .semantics { contentDescription = cdStarRow },
        ) {
            for (star in 1..5) {
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onStarSelected(star) },
                )
            }
        }
    }
}
