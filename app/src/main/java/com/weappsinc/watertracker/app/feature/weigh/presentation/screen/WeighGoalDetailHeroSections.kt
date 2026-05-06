package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

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
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens

@Composable
internal fun WeighGoalDetailHeroTopRow(
    imageLoader: ImageLoader,
    onEditTarget: () -> Unit,
    onHero: Color,
    modifier: Modifier = Modifier
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = AssetPaths.GOAL_ICON,
            contentDescription = AppText.WEIGH_GOAL_CARD_TITLE,
            imageLoader = imageLoader,
            modifier = Modifier.size(WeighDimens.SheetHeaderIconSize),
            contentScale = ContentScale.Fit
        )
        Text(
            text = AppText.WEIGH_GOAL_CARD_TITLE,
            modifier = Modifier.padding(start = 10.dp),
            style = AppTypography.Title3,
            color = onHero,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(WeighDimens.WeighDetailHeroEditSize)
                .clip(RoundedCornerShape(WeighDimens.WeighDetailHeroEditCorner))
                .background(AppColors.WeighDetailHeroEditButtonBg)
                .clickable(onClick = onEditTarget),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = AppText.ADJUST,
                tint = AppColors.WeighDetailHeroEditIcon,
                modifier = Modifier.size(WeighDimens.WeighDetailHeroEditIconSize)
            )
        }
    }
}

@Composable
internal fun WeighGoalDetailHeroStatsRow(
    targetValueText: String,
    massUnitLabel: String,
    gapValueText: String,
    onHero: Color,
    modifier: Modifier = Modifier
) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = targetValueText,
                style = AppTypography.WeighDetailHeroTargetValue,
                color = onHero
            )
            Text(
                text = " $massUnitLabel",
                style = AppTypography.WeighDetailHeroGapValue,
                color = onHero,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = AppText.WEIGH_GAP_LABEL,
                style = AppTypography.WeighDetailHeroLabel,
                color = onHero
            )
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = gapValueText,
                    style = AppTypography.WeighDetailHeroGapValue,
                    color = onHero
                )
                Text(
                    text = " $massUnitLabel",
                    style = AppTypography.WeighDetailHeroLabel,
                    color = onHero,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            }
        }
    }
}
