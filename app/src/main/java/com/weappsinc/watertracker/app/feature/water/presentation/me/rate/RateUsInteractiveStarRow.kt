package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens

/** Hàng 5 sao chạm độc lập — khớp mock (chữ rồi mới tới sao). */
@Composable
fun RateUsInteractiveStarRow(
    selectedStars: Int,
    onStarSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cdStarRow = stringResource(R.string.cd_rate_star_row)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = cdStarRow },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (star in 1..5) {
            val filled = star <= selectedStars
            IconButton(
                onClick = { onStarSelected(star) },
                modifier = Modifier.size(AppDimens.RateUsStarTouchMinWidth),
            ) {
                Icon(
                    imageVector = if (filled) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if (filled) AppColors.RateUsStarFilled else AppColors.RateUsStarEmpty,
                    modifier = Modifier.size(AppDimens.RateUsStarIconSize),
                )
            }
        }
    }
}
