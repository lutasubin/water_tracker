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

/**
 * Số ml hiển thị cho màn Goal: khi [targetGoalMl] đổi thì chạy từng số tăng/giảm trong
 * ~[WaterConstants.INTAKE_COUNT_UP_MS]; lần đầu đồng bộ ngay để tránh nhảy từ 0.
 */
@Composable
fun rememberDisplayGoalMl(targetGoalMl: Int): Int {
    var displayedGoalMl by remember { mutableIntStateOf(0) }
    var skipCountUp by remember { mutableStateOf(true) }
    LaunchedEffect(targetGoalMl) {
        val target = targetGoalMl
        if (skipCountUp) {
            displayedGoalMl = target
            skipCountUp = false
            return@LaunchedEffect
        }
        if (target == displayedGoalMl) return@LaunchedEffect
        val start = displayedGoalMl
        val durationMs = WaterConstants.INTAKE_COUNT_UP_MS.toLong()
        val startTime = withFrameMillis { it }
        while (true) {
            val now = withFrameMillis { it }
            val elapsed = now - startTime
            if (elapsed >= durationMs) {
                displayedGoalMl = target
                break
            }
            val fraction = (elapsed.toFloat() / durationMs).coerceIn(0f, 1f)
            val eased = FastOutSlowInEasing.transform(fraction)
            val animated = start + (target - start) * eased
            displayedGoalMl = animated.roundToInt()
        }
    }
    return displayedGoalMl
}
