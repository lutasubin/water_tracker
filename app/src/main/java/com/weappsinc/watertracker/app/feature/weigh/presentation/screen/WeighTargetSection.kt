package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens

@Composable
fun WeighTargetSection(
    hasTarget: Boolean,
    targetValueText: String?,
    massUnitLabel: String,
    gapValueText: String,
    journeyProgressFraction: Float,
    journeyProgressPercent: Int,
    imageLoader: ImageLoader,
    onOpenTargetSheet: () -> Unit,
    onOpenGoalDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = WeighDimens.ScreenHorizontalPadding)
    ) {
        Text(
            text = stringResource(R.string.target_weight_section_title),
            color = AppColors.HomeTitle,
            style = AppTypography.Title2
        )
        Spacer(Modifier.height(WeighDimens.TargetSectionTopSpacing))
        if (hasTarget && targetValueText != null) {
            WeighGoalCard(
                targetValueText = targetValueText,
                massUnitLabel = massUnitLabel,
                gapValueText = gapValueText,
                journeyProgressFraction = journeyProgressFraction,
                journeyProgressPercent = journeyProgressPercent,
                imageLoader = imageLoader,
                onClick = onOpenGoalDetail
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
                    .background(AppColors.HomeCard)
                    .padding(AppDimens.HomeCardInnerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = AssetPaths.NO_GOAL_ICON,
                    contentDescription = stringResource(R.string.target_weight_empty),
                    imageLoader = imageLoader,
                    modifier = Modifier.size(WeighDimens.TargetEmptyIconSize),
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.height(WeighDimens.TargetCtaTopSpacing))
                Text(
                    text = stringResource(R.string.target_weight_empty),
                    color = AppColors.HomeMuted,
                    style = AppTypography.BodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(WeighDimens.TargetCtaTopSpacing))
                Button(
                    onClick = onOpenTargetSheet,
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.HomePrimary)
                ) {
                    Text(stringResource(R.string.create_target), color = AppColors.GenderSelectedContent)
                }
            }
        }
    }
}
