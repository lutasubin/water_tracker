package com.weappsinc.watertracker.app.feature.water.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

@Composable
fun HomeBottomBar(
    selected: HomeTab,
    onSelect: (HomeTab) -> Unit,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = AppColors.HomeCard,
        tonalElevation = AppDimens.HomeBottomBarTonalElevation
    ) {
        Column {
            HorizontalDivider(color = AppColors.HomeProgressTrack)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimens.HomeBottomNavHeight)
                    .padding(horizontal = AppDimens.HomeHorizontalPadding),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavItem(
                    label = AppText.HOME_TAB_WATER,
                    iconPath = AssetPaths.HOME_WATER_ICON,
                    selected = selected == HomeTab.Water,
                    imageLoader = imageLoader,
                    onClick = { onSelect(HomeTab.Water) }
                )
                NavItem(
                    label = AppText.HOME_TAB_BMI,
                    iconPath = AssetPaths.HOME_BMI_ICON,
                    selected = selected == HomeTab.Bmi,
                    imageLoader = imageLoader,
                    onClick = { onSelect(HomeTab.Bmi) }
                )
                NavItem(
                    label = AppText.HOME_TAB_ME,
                    iconPath = AssetPaths.HOME_INFOR_ICON,
                    selected = selected == HomeTab.Me,
                    imageLoader = imageLoader,
                    onClick = { onSelect(HomeTab.Me) }
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    iconPath: String,
    selected: Boolean,
    imageLoader: ImageLoader,
    onClick: () -> Unit
) {
    val tint = if (selected) AppColors.HomePrimary else AppColors.HomeNavInactive
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = iconPath,
            contentDescription = label,
            imageLoader = imageLoader,
            modifier = Modifier.size(AppDimens.HomeNavIconSize),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(tint)
        )
        Text(
            text = label,
            color = tint,
            style = AppTypography.BodyMedium
        )
    }
}
