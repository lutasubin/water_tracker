package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.background
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

/** Thẻ nhắc nhở: cùng chiều cao tối thiểu với [GoalStatCard]; giờ căn giữa ([AppTypography.StatCardValue]). */
@Composable
fun ReminderStatCard(
    imageLoader: ImageLoader,
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
            AsyncImage(
                model = AssetPaths.NOTIE_ICON,
                contentDescription = null,
                imageLoader = imageLoader,
                modifier = Modifier.size(AppDimens.HomeStatCardIconSize),
                contentScale = ContentScale.Fit
            )
            Text(
                text = stringResource(R.string.reminder_card_label),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = AppColors.HomeMuted,
                style = AppTypography.BodyMedium
            )
            Spacer(Modifier.size(AppDimens.HomeStatEditButtonSize))
        }
        Spacer(Modifier.height(AppDimens.HomeStatValueSpacing))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.reminder_sample_time),
                color = AppColors.HomeTitle,
                style = AppTypography.StatCardValue,
                textAlign = TextAlign.Center
            )
        }
    }
}
