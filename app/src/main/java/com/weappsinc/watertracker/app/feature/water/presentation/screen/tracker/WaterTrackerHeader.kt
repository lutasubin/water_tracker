package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Thanh đầu trang: tiêu đề trái + pill streak (đổ bóng) phải. */
@Composable
fun WaterTrackerHeader(
    streakDays: Int,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AppText.TRACKER_TITLE,
            modifier = Modifier.weight(1f),
            color = AppColors.HomeTitle,
            style = AppTypography.Title2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.width(AppDimens.HomeHeaderTitleStreakGap))
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = AppColors.HomeStreakPillBg,
            shadowElevation = AppDimens.HomeStreakPillShadowElevation
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = AssetPaths.STREAK_ICON,
                    contentDescription = null,
                    imageLoader = imageLoader,
                    modifier = Modifier.size(20.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "$streakDays ${AppText.STREAK_SUFFIX}",
                    color = AppColors.HomeStreakPillText,
                    style = AppTypography.BodyMedium
                )
            }
        }
    }
}
