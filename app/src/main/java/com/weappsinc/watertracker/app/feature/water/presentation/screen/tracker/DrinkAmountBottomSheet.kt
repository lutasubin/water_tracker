package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.constants.AppText
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
    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = AppColors.HomeCard) {
        Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = AppText.DRINK_AMOUNT_TITLE, color = AppColors.HomeTitle, style = AppTypography.Title2)
                Icon(Icons.Outlined.Close, contentDescription = AppText.CLOSE, tint = AppColors.HomeTitle, modifier = Modifier.clickable(onClick = onDismiss))
            }
            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AdjustButton(symbol = "-", onClick = onDecrease)
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = amountMl.toString(), color = AppColors.HomeTitle, style = AppTypography.WaterGoalValue)
                    Spacer(Modifier.size(6.dp))
                    Text(text = AppText.UNIT_ML, color = AppColors.HomeTitle, style = AppTypography.Title3)
                }
                AdjustButton(symbol = "+", onClick = onIncrease)
            }
            Spacer(Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                presets.forEach { preset ->
                    val selected = preset.amountMl == amountMl
                    Surface(
                        modifier = Modifier.weight(1f).clickable { onSelectPreset(preset.amountMl) },
                        shape = RoundedCornerShape(18.dp),
                        color = if (selected) AppColors.HomePrimary.copy(alpha = 0.10f) else AppColors.GenderUnselectedBackground,
                        border = if (selected) androidx.compose.foundation.BorderStroke(1.dp, AppColors.HomePrimary) else null
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = preset.iconPath,
                                contentDescription = null,
                                imageLoader = imageLoader,
                                modifier = Modifier.size(34.dp),
                                contentScale = ContentScale.Fit,
                                colorFilter = ColorFilter.tint(if (selected) AppColors.HomePrimary else AppColors.HomeMuted)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "${preset.amountMl} ${AppText.UNIT_ML}",
                                color = if (selected) AppColors.HomePrimary else AppColors.HomeMuted,
                                style = AppTypography.BodyLarge,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                TrackerDrinkButton(onClick = onDrink)
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun AdjustButton(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(AppColors.HomeProgressTrack, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = symbol, color = AppColors.HomePrimary, style = AppTypography.Title2)
    }
}
