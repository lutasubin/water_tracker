package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens

/** Thẻ mục tiêu trên Weigh Tracker (nền trắng, gọn). */
@Composable
fun WeighGoalCard(
    targetValueText: String,
    massUnitLabel: String,
    gapValueText: String,
    journeyProgressFraction: Float,
    journeyProgressPercent: Int,
    imageLoader: ImageLoader,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .defaultMinSize(minHeight = AppDimens.HomeStatCardMinHeight)
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .background(AppColors.HomeCard)
            .padding(AppDimens.HomeCardInnerPadding)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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
                color = AppColors.HomeTitle,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = targetValueText,
                    style = AppTypography.StatCardValue,
                    color = AppColors.HomeTitle
                )
                Text(
                    text = " $massUnitLabel",
                    style = AppTypography.BodyMedium,
                    color = AppColors.WeighGoalLabelMuted,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = AppText.WEIGH_GAP_LABEL,
                    style = AppTypography.BodyMedium,
                    color = AppColors.WeighGoalLabelMuted,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = gapValueText,
                        style = AppTypography.Title3,
                        color = AppColors.HomeTitle,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = " $massUnitLabel",
                        style = AppTypography.BodyMedium,
                        color = AppColors.WeighGoalLabelMuted,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(18.dp))
        WeighGoalProgressBar(journeyProgressFraction)
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = AppText.WEIGH_JOURNEY_PROGRESS_LABEL,
                style = AppTypography.BodyMedium,
                color = AppColors.WeighGoalLabelMuted,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${journeyProgressPercent}%",
                style = AppTypography.BodyLarge,
                color = AppColors.HomeTitle,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
