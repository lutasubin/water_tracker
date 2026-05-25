package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/** Nền sóng theo repo tham chiếu: nhiều lớp filled-wave chạy liên tục theo frame. */
@Composable
fun WaterProgressWaveBackground(modifier: Modifier = Modifier) {
    val elapsedMs by rememberWaterProgressWaveElapsedMs()
    Canvas(modifier.graphicsLayer { clip = true }) {
        drawWaterProgressWaveLayers(size = size, elapsedMs = elapsedMs)
        drawWaterProgressDepthOverlay(size = size)
    }
}
