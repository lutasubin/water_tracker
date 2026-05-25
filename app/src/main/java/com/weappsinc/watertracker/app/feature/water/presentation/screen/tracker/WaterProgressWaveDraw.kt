package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import com.weappsinc.watertracker.app.core.theme.AppColors
import kotlin.math.PI
import kotlin.math.sin

private const val BOB_CYCLE_MS = 4_800L

/** Phủ nhẹ phần đáy card để giữ cảm giác có chiều sâu. */
internal fun DrawScope.drawWaterProgressDepthOverlay(size: Size) {
    if (size.width <= 0f || size.height <= 0f) return
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(
                0f to Color.White.copy(alpha = 0.07f),
                0.18f to Color.Transparent,
                0.52f to AppColors.HomeHeroDepthOverlay.copy(alpha = 0.04f),
                1f to AppColors.HomeHeroDepthOverlay.copy(alpha = 0.18f),
            ),
            startY = 0f,
            endY = size.height,
        ),
    )
}

internal fun DrawScope.drawWaterProgressWaveLayers(
    size: Size,
    elapsedMs: Long,
    layers: List<WaterProgressWaveDrawLayer> = waterProgressWaveLayers,
) {
    if (size.width <= 1f || size.height <= 2f || layers.isEmpty()) return
    clipRect(0f, 0f, size.width, size.height) {
        val width = size.width
        val height = size.height
        val bobRad = (elapsedMs % BOB_CYCLE_MS).toFloat() / BOB_CYCLE_MS * (2.0 * PI).toFloat()
        val minBaseline = height * 0.08f
        val maxBaseline = (height * 0.78f).coerceAtLeast(minBaseline)
        layers.forEachIndexed { index, layer ->
            val def = layer.def
            val phaseT = layerPhaseT(elapsedMs = elapsedMs, cycleMs = def.durationMs, phaseStart = def.phaseStart)
            val wavelength = width * def.wavelengthWidthRatio
            if (wavelength <= 0f) return@forEachIndexed
            val direction = if (def.reverse) -1f else 1f
            val phasePx = direction * phaseT * wavelength
            val bobPx = sin(bobRad + index * 0.9f) * height * 0.006f
            val baselineY = (height * def.baselineRatio + bobPx).coerceIn(minBaseline, maxBaseline)
            val path = buildWaveBandPath(
                width = width,
                height = height,
                baselineY = baselineY,
                amplitudePx = height * def.amplitudeRatio,
                thicknessPx = height * def.thicknessRatio,
                wavelengthPx = wavelength,
                phasePx = phasePx,
                topEdgeDriftPx = wavelength * def.topEdgeDriftRatio,
                bottomEdgeDriftPx = wavelength * def.bottomEdgeDriftRatio,
                harmonic2AmpScale = def.harmonic2AmpScale,
                harmonic2WaveScale = def.harmonic2WaveScale,
            )
            val topY = (baselineY - height * def.amplitudeRatio).coerceAtLeast(0f)
            val bottomY = (baselineY + height * (def.amplitudeRatio + def.thicknessRatio)).coerceAtMost(height)
            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0f to layer.topColor.copy(alpha = layer.alpha),
                        0.76f to layer.bottomColor.copy(alpha = layer.alpha * 0.78f),
                        1f to layer.bottomColor.copy(alpha = layer.alpha * 0.48f),
                    ),
                    startY = topY,
                    endY = bottomY,
                ),
            )
        }
    }
}

private fun buildWaveBandPath(
    width: Float,
    height: Float,
    baselineY: Float,
    amplitudePx: Float,
    thicknessPx: Float,
    wavelengthPx: Float,
    phasePx: Float,
    topEdgeDriftPx: Float,
    bottomEdgeDriftPx: Float,
    harmonic2AmpScale: Float,
    harmonic2WaveScale: Float,
): Path {
    if (width <= 0f || height <= 0f || wavelengthPx <= 0f || thicknessPx <= 0f) return Path()
    val padX = wavelengthPx * 0.65f
    val startX = -padX
    val endX = width + padX
    val wave2Len = (wavelengthPx * harmonic2WaveScale).coerceAtLeast(wavelengthPx * 0.4f)
    val amp2 = amplitudePx * harmonic2AmpScale
    val step = (wavelengthPx / 64f).coerceIn(1f, 4f)
    fun waveY(x: Float, driftPx: Float, ampScale: Float): Float {
        val t1 = 2.0 * PI * (x + phasePx + driftPx) / wavelengthPx
        val t2 = 2.0 * PI * (x + phasePx * 0.92f + driftPx * 0.58f) / wave2Len
        return baselineY + amplitudePx * ampScale * sin(t1).toFloat() + amp2 * sin(t2).toFloat()
    }
    return Path().apply {
        moveTo(startX, waveY(startX, topEdgeDriftPx, 1f))
        var x = startX + step
        while (x <= endX) {
            lineTo(x, waveY(x, topEdgeDriftPx, 1f))
            x += step
        }
        x = endX
        while (x >= startX) {
            lineTo(
                x,
                (waveY(x, bottomEdgeDriftPx, 0.46f) + thicknessPx).coerceAtMost(height + thicknessPx),
            )
            x -= step
        }
        close()
    }
}
