package com.weappsinc.watertracker.app.core.ads

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.theme.AppColors

@Composable
fun BannerAdLoadingPlaceholder(modifier: Modifier = Modifier) {
    AdsShimmerBox(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(12.dp),
    )
}

@Composable
fun NativeAdLoadingPlaceholder(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = AppColors.HomeCard,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AdsShimmerBox(Modifier.size(52.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AdsShimmerBox(Modifier.fillMaxWidth().height(18.dp))
                    AdsShimmerBox(Modifier.fillMaxWidth(0.72f).height(14.dp))
                }
            }
            AdsShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            )
            Spacer(Modifier.height(2.dp))
        }
    }
}
