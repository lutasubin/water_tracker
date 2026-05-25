package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.ui.graphics.Color
import com.weappsinc.watertracker.app.core.theme.AppColors

internal data class WaterProgressWaveLayerDef(
    val baselineRatio: Float,
    val amplitudeRatio: Float,
    val thicknessRatio: Float,
    val wavelengthWidthRatio: Float,
    val durationMs: Int,
    val reverse: Boolean = false,
    val phaseStart: Float = 0f,
    val topEdgeDriftRatio: Float = 0.05f,
    val bottomEdgeDriftRatio: Float = 0.10f,
    val harmonic2AmpScale: Float = 0.12f,
    val harmonic2WaveScale: Float = 0.82f,
)

internal data class WaterProgressWaveDrawLayer(
    val def: WaterProgressWaveLayerDef,
    val topColor: Color,
    val bottomColor: Color,
    val alpha: Float,
)

/** Giữ 3 dải sóng chính để card thoáng và dễ nhìn hơn. */
internal val waterProgressWaveLayers = listOf(
    WaterProgressWaveDrawLayer(
        def = WaterProgressWaveLayerDef(0.17f, 0.030f, 0.12f, 1.30f, 12_500, phaseStart = 0f),
        topColor = AppColors.HomeHeroWaveTint1,
        bottomColor = AppColors.HomeHeroWaveTint3,
        alpha = 0.34f,
    ),
    WaterProgressWaveDrawLayer(
        def = WaterProgressWaveLayerDef(0.40f, 0.038f, 0.16f, 1.02f, 10_000, reverse = true, phaseStart = 0.24f),
        topColor = AppColors.HomeHeroWaveTint2,
        bottomColor = AppColors.HomeHeroWaveTint4,
        alpha = 0.24f,
    ),
    WaterProgressWaveDrawLayer(
        def = WaterProgressWaveLayerDef(0.64f, 0.032f, 0.22f, 0.82f, 8_000, phaseStart = 0.58f),
        topColor = AppColors.HomeHeroWaveTint4,
        bottomColor = AppColors.HomeHeroWaveTint6,
        alpha = 0.16f,
    ),
)
