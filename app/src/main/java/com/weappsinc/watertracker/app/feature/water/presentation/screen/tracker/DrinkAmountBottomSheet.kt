package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.ads.AppBannerSection
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.constants.WaterConstants
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography

private data class DrinkPreset(val amountMl: Int, val iconPath: String)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DrinkAmountBottomSheet(
    amountMl: Int,
    imageLoader: ImageLoader,
    onDismiss: () -> Unit,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    onSelectPreset: (Int) -> Unit,
    onDrink: () -> Unit
) {
    val presets = listOf(
        DrinkPreset(WaterConstants.PRESET_DRINK_150, AssetPaths.DRINK_150_ICON),
        DrinkPreset(WaterConstants.PRESET_DRINK_200, AssetPaths.DRINK_200_ICON),
        DrinkPreset(WaterConstants.PRESET_DRINK_500, AssetPaths.DRINK_500_ICON)
    )
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppColors.HomeCard,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.drink_amount_title),
                        color = AppColors.HomeTitle,
                        style = AppTypography.Title3,
                    )
                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = stringResource(R.string.close),
                        tint = AppColors.HomeTitle,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable(onClick = onDismiss),
                    )
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AdjustButton(symbol = "-", onClick = onDecrease)
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(text = amountMl.toString(), color = AppColors.HomeTitle, style = AppTypography.WaterGoalValue)
                        Spacer(Modifier.size(6.dp))
                        Text(text = stringResource(R.string.unit_ml), color = AppColors.HomeTitle, style = AppTypography.Title3)
                    }
                    AdjustButton(symbol = "+", onClick = onIncrease)
                }
                Spacer(Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    presets.forEach { preset ->
                        val selected = preset.amountMl == amountMl
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onSelectPreset(preset.amountMl) },
                            shape = RoundedCornerShape(16.dp),
                            color = if (selected) AppColors.HomePrimary.copy(alpha = 0.10f) else AppColors.GenderUnselectedBackground,
                            border = if (selected) BorderStroke(1.dp, AppColors.HomePrimary) else null
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = preset.iconPath,
                                    contentDescription = null,
                                    imageLoader = imageLoader,
                                    modifier = Modifier.size(28.dp),
                                    contentScale = ContentScale.Fit,
                                    colorFilter = ColorFilter.tint(if (selected) AppColors.HomePrimary else AppColors.HomeMuted)
                                )
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    text = "${preset.amountMl} ${stringResource(R.string.unit_ml)}",
                                    color = if (selected) AppColors.HomePrimary else AppColors.HomeMuted,
                                    style = AppTypography.BodyMedium,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(14.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    TrackerDrinkButton(
                        onClick = onDrink,
                        width = 152.dp,
                        height = 48.dp,
                        iconSize = 18.dp,
                        contentGap = 6.dp,
                    )
                }
                Spacer(Modifier.height(4.dp))
            }
            AppBannerSection()
        }
    }
}

@Composable
private fun AdjustButton(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(AppColors.HomeProgressTrack, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = symbol, color = AppColors.HomePrimary, style = AppTypography.Title3)
    }
}
