package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens

@Composable
fun WeighStatCard(
    iconPath: String,
    label: String,
    primary: String,
    unit: String,
    imageLoader: ImageLoader,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .defaultMinSize(minHeight = AppDimens.HomeStatCardMinHeight)
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .background(AppColors.HomeCard)
            .padding(AppDimens.HomeCardInnerPadding)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = iconPath,
                contentDescription = null,
                imageLoader = imageLoader,
                modifier = Modifier.size(WeighDimens.CardIconSize),
                contentScale = ContentScale.Fit
            )
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = AppColors.HomeMuted,
                style = AppTypography.BodyMedium
            )
            Box(
                modifier = Modifier
                    .size(AppDimens.HomeStatEditButtonSize)
                    .clip(RoundedCornerShape(AppDimens.HomeStatEditButtonCorner))
                    .background(AppColors.HomeStatEditButtonBg)
                    .clickable(onClick = onEdit),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = label,
                    tint = AppColors.HomePrimary,
                    modifier = Modifier.size(AppDimens.HomeStatEditIconSize)
                )
            }
        }
        Spacer(Modifier.height(AppDimens.HomeStatValueSpacing))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = primary, color = AppColors.HomeTitle, style = AppTypography.StatCardValue)
            Text(text = " $unit", color = AppColors.HomeMuted, style = AppTypography.BodyMedium)
        }
    }
}
