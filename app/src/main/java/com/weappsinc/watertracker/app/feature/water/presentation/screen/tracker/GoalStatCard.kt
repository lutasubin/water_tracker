package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/**
 * Thẻ mục tiêu Water: hàng đầu icon + nhãn trái, ô sửa phải; dưới số ml/L lớn căn trái (thiết kế mock).
 */
@Composable
fun GoalStatCard(
    goalDisplayCompact: String,
    imageLoader: ImageLoader,
    onEditGoal: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .defaultMinSize(minHeight = AppDimens.HomeStatCardMinHeight)
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .background(AppColors.HomeCard)
            .padding(AppDimens.HomeCardInnerPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimens.HomeGoalStatIconLabelGap)
            ) {
                AsyncImage(
                    model = AssetPaths.GOAL_ICON,
                    contentDescription = stringResource(R.string.cd_goal_icon),
                    imageLoader = imageLoader,
                    modifier = Modifier.size(AppDimens.HomeStatCardIconSize),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = stringResource(R.string.water_goal_card_label),
                    modifier = Modifier.weight(1f),
                    color = AppColors.HomeMuted,
                    style = AppTypography.BodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Box(
                modifier = Modifier
                    .size(AppDimens.HomeStatEditButtonSize)
                    .clip(RoundedCornerShape(AppDimens.HomeStatEditButtonCorner))
                    .background(AppColors.HomeStatEditButtonBg)
                    .clickable(onClick = onEditGoal),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(R.string.water_goal_card_label),
                    tint = AppColors.HomePrimary,
                    modifier = Modifier.size(AppDimens.HomeStatEditIconSize)
                )
            }
        }
        Spacer(Modifier.height(AppDimens.HomeStatValueSpacing))
        Text(
            text = goalDisplayCompact,
            modifier = Modifier.fillMaxWidth(),
            color = AppColors.HomeTitle,
            style = AppTypography.DisplayNumber,
            textAlign = TextAlign.Start
        )
    }
}
