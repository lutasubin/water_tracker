package com.weappsinc.watertracker.app.core.ads

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.theme.AppColors

@Composable
fun AppBannerSection(
    modifier: Modifier = Modifier,
    placement: BannerPlacement = BannerPlacement.Home,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = AppColors.HomeCard,
    ) {
        androidx.compose.foundation.layout.Column {
            HorizontalDivider(color = AppColors.HomeProgressTrack)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center,
            ) {
                AppBannerAd(placement = placement)
            }
        }
    }
}
