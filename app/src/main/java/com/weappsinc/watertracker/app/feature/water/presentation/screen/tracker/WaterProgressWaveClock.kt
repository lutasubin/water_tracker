package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.LongState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import kotlinx.coroutines.isActive

/** Đồng hồ frame liên tục để phase sóng chạy mượt, không reset khựng. */
@Composable
internal fun rememberWaterProgressWaveElapsedMs(): LongState {
    val elapsed = remember { mutableLongStateOf(0L) }
    LaunchedEffect(Unit) {
        val originNs = withFrameNanos { it }
        while (isActive) {
            withFrameNanos { frameNs ->
                elapsed.longValue = (frameNs - originNs) / 1_000_000L
            }
        }
    }
    return elapsed
}

internal fun layerPhaseT(elapsedMs: Long, cycleMs: Int, phaseStart: Float): Float {
    if (cycleMs <= 0) return phaseStart
    val t = (elapsedMs % cycleMs.toLong()).toFloat() / cycleMs.toFloat()
    return fract(t + phaseStart)
}

private fun fract(value: Float): Float {
    val fraction = value - value.toInt()
    return if (fraction < 0f) fraction + 1f else fraction
}
