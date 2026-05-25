package com.weappsinc.watertracker.app.core.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.weappsinc.watertracker.app.core.theme.AppColors
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun AppBlueWaveBackground(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "app_wave_bg")
    val slowPhase by transition.animateFloat(0f, 1f, infiniteRepeatable(tween(16000, easing = LinearEasing), RepeatMode.Restart), "slow_phase")
    val midPhase by transition.animateFloat(0f, 1f, infiniteRepeatable(tween(11000, easing = LinearEasing), RepeatMode.Restart), "mid_phase")
    val fastPhase by transition.animateFloat(0f, 1f, infiniteRepeatable(tween(8000, easing = LinearEasing), RepeatMode.Restart), "fast_phase")
    Canvas(modifier) {
        drawRect(
            brush = Brush.verticalGradient(
                listOf(AppColors.HomeHeroGradientTop, AppColors.HomeHeroGradientMid, AppColors.HomeHeroGradientBottom),
            ),
        )
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color.White.copy(alpha = 0.14f), Color.Transparent),
                center = Offset(size.width * 0.22f, size.height * 0.20f),
                radius = size.minDimension * 0.72f,
            ),
            radius = size.minDimension * 0.72f,
            center = Offset(size.width * 0.22f, size.height * 0.20f),
        )
        drawWaveBand(0.22f, 0.022f, 0.10f, 1.45f, slowPhase, AppColors.HomeHeroWaveTint1, AppColors.HomeHeroWaveTint3, 0.22f)
        drawWaveBand(0.42f, 0.030f, 0.12f, 1.10f, midPhase, AppColors.HomeHeroWaveTint2, AppColors.HomeHeroWaveTint4, 0.18f, true)
        drawWaveBand(0.68f, 0.034f, 0.16f, 0.84f, fastPhase, AppColors.HomeHeroWaveTint4, AppColors.HomeHeroWaveTint6, 0.16f)
    }
}

private fun DrawScope.drawWaveBand(
    baselineRatio: Float,
    amplitudeRatio: Float,
    thicknessRatio: Float,
    wavelengthRatio: Float,
    phase: Float,
    topColor: Color,
    bottomColor: Color,
    alpha: Float,
    reverse: Boolean = false,
) {
    if (size.width <= 0f || size.height <= 0f) return
    val baselineY = size.height * baselineRatio
    val amplitudePx = size.height * amplitudeRatio
    val thicknessPx = size.height * thicknessRatio
    val wavelengthPx = size.width * wavelengthRatio
    val direction = if (reverse) -1f else 1f
    val phasePx = direction * phase * wavelengthPx
    val wave2Len = wavelengthPx * 0.78f
    val step = (wavelengthPx / 72f).coerceIn(1f, 4f)
    fun waveY(x: Float, drift: Float, ampScale: Float): Float {
        val t1 = 2.0 * PI * (x + phasePx + drift) / wavelengthPx
        val t2 = 2.0 * PI * (x + phasePx * 0.85f + drift * 0.45f) / wave2Len
        return baselineY + amplitudePx * ampScale * sin(t1).toFloat() + amplitudePx * 0.10f * sin(t2).toFloat()
    }
    val startX = -wavelengthPx * 0.45f
    val endX = size.width + wavelengthPx * 0.45f
    val path = Path().apply {
        moveTo(startX, waveY(startX, wavelengthPx * 0.04f, 1f))
        var x = startX + step
        while (x <= endX) {
            lineTo(x, waveY(x, wavelengthPx * 0.04f, 1f))
            x += step
        }
        x = endX
        while (x >= startX) {
            lineTo(x, waveY(x, wavelengthPx * 0.10f, 0.44f) + thicknessPx)
            x -= step
        }
        close()
    }
    drawPath(
        path = path,
        brush = Brush.verticalGradient(
            colors = listOf(topColor.copy(alpha = alpha), bottomColor.copy(alpha = alpha * 0.78f)),
            startY = baselineY - amplitudePx,
            endY = baselineY + thicknessPx + amplitudePx,
        ),
    )
}
