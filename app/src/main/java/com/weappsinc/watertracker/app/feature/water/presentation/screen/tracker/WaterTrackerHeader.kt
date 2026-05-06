package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Thanh đầu trang: tiêu đề trái + pill streak Lottie (Fire) phải. */
@Composable
fun WaterTrackerHeader(
    streakDays: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.water_tracker_title),
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
                StreakFireLottie(Modifier.size(20.dp))
                Spacer(Modifier.width(6.dp))
                Text(
                    text = pluralStringResource(R.plurals.water_streak_pill, streakDays, streakDays),
                    color = AppColors.HomeStreakPillText,
                    style = AppTypography.BodyMedium
                )
            }
        }
    }
}
