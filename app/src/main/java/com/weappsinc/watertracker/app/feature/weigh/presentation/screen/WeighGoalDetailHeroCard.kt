package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import coil.ImageLoader
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens

@Composable
fun WeighGoalDetailHeroCard(
    targetValueText: String,
    massUnitLabel: String,
    gapValueText: String,
    journeyProgressFraction: Float,
    journeyProgressPercent: Int,
    imageLoader: ImageLoader,
    onEditTarget: () -> Unit,
    modifier: Modifier = Modifier
) {
    val onHero = Color.White
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(WeighDimens.WeighDetailHeroCorner))
            .background(AppColors.WeighGoalDetailHeroBg)
            .padding(WeighDimens.WeighDetailHeroPadding)
    ) {
        WeighGoalDetailHeroTopRow(imageLoader, onEditTarget, onHero)
        Spacer(Modifier.height(18.dp))
        WeighGoalDetailHeroStatsRow(targetValueText, massUnitLabel, gapValueText, onHero)
        Spacer(Modifier.height(20.dp))
        WeighGoalProgressBar(
            fraction = journeyProgressFraction,
            trackColor = AppColors.WeighDetailHeroProgressTrack,
            fillColor = AppColors.WeighDetailHeroProgressFill,
            barHeight = WeighDimens.WeighDetailHeroProgressHeight
        )
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.weigh_journey_progress_label),
                style = AppTypography.WeighDetailHeroLabel,
                color = onHero
            )
            Text(
                text = "${journeyProgressPercent}%",
                style = AppTypography.BodyLarge.copy(fontWeight = FontWeight.Bold),
                color = onHero
            )
        }
    }
}
