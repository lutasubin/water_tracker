package com.weappsinc.watertracker.app.feature.water.presentation.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import com.weappsinc.watertracker.app.core.constants.WaterConstants
import kotlin.math.roundToInt

/**
 * Số ml hiển thị cho tiến độ: mỗi lần [todayIntakeMl] tăng sẽ chạy từng số ~[INTAKE_COUNT_UP_MS];
 * lần đầu / khi giảm thì bỏ qua hiệu ứng.
 */
@Composable
fun rememberDisplayIntakeMl(todayIntakeMl: Int): Int {
    var displayedIntakeMl by remember { mutableIntStateOf(0) }
    var skipCountUp by remember { mutableStateOf(true) }
    LaunchedEffect(todayIntakeMl) {
        val target = todayIntakeMl
        if (skipCountUp) {
            displayedIntakeMl = target
            skipCountUp = false
            return@LaunchedEffect
        }
        if (target > displayedIntakeMl) {
            val start = displayedIntakeMl
            val durationMs = WaterConstants.INTAKE_COUNT_UP_MS.toLong()
            val startTime = withFrameMillis { it }
            while (true) {
                val now = withFrameMillis { it }
                val elapsed = now - startTime
                if (elapsed >= durationMs) {
                    displayedIntakeMl = target
                    break
                }
                val fraction = (elapsed.toFloat() / durationMs).coerceIn(0f, 1f)
                val eased = FastOutSlowInEasing.transform(fraction)
                displayedIntakeMl =
                    (start + (target - start) * eased).roundToInt().coerceIn(start, target)
            }
        } else {
            displayedIntakeMl = target
        }
    }
    return displayedIntakeMl
}
