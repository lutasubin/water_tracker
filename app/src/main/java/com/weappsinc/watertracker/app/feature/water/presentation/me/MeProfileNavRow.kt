package com.weappsinc.watertracker.app.feature.water.presentation.me

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Một hàng settings tab Me: icon SVG, nhãn, giá trị primary, chevron. */
@Composable
internal fun MeProfileNavRow(
    iconPath: String,
    label: String,
    valueText: String,
    imageLoader: ImageLoader,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = "$label, $valueText" }
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .background(AppColors.HomeCard)
            .clickable(role = Role.Button, onClick = onClick)
            .height(AppDimens.MeProfileMenuRowHeight)
            .padding(horizontal = AppDimens.HomeCardInnerPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = iconPath,
            contentDescription = null,
            imageLoader = imageLoader,
            modifier = Modifier.size(22.dp),
            contentScale = ContentScale.Fit,
        )
        Text(
            text = label,
            style = AppTypography.BodyLarge,
            color = AppColors.HomeTitle,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
        )
        Text(
            text = valueText,
            style = AppTypography.BodyLarge,
            color = AppColors.HomePrimary,
            modifier = Modifier.padding(end = 4.dp),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = null,
            tint = AppColors.HomeMuted,
            modifier = Modifier.size(22.dp),
        )
    }
}
