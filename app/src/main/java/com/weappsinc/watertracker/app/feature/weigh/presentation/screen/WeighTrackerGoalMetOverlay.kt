package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.weappsinc.watertracker.app.core.components.FireworksCelebrationOverlay
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeightGoalCompletionSnapshot
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighTrackerViewModel
import java.time.LocalDate

/**
 * Pháo hoa + dialog khi cân thân khớp mục tiêu (đã snap 0,5 kg).
 * Mỗi cycle (target, journeyStart) chỉ bắn 1 lần — cycle reset khi target hoặc journey đổi.
 */
@Composable
fun WeighTrackerGoalMetOverlay(
    vm: WeighTrackerViewModel,
    bodyWeightKg: Float,
    targetWeightKg: Float?,
    journeyStartWeightKg: Float?,
    targetValueText: String?,
) {
    val snappedTargetKg = targetWeightKg?.let { MassDisplay.snapTargetKg(it) }
    val isTargetMet = targetWeightKg != null &&
        targetWeightKg > 0f &&
        bodyWeightKg > 0f &&
        MassDisplay.snapTargetKg(bodyWeightKg) == MassDisplay.snapTargetKg(targetWeightKg)

    var armed by remember { mutableStateOf(true) }
    var archivedThisCycle by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showFireworks by remember { mutableStateOf(false) }
    var fireworksSession by remember { mutableIntStateOf(0) }

    // Cycle mới (target hoặc journey đổi) → re-arm cho phép pháo hoa bắn lại.
    LaunchedEffect(targetWeightKg, journeyStartWeightKg) {
        armed = true
        archivedThisCycle = false
    }

    LaunchedEffect(isTargetMet, snappedTargetKg) {
        if (armed && isTargetMet && targetWeightKg != null) {
            armed = false
            fireworksSession++
            showFireworks = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (showFireworks) {
            key(fireworksSession) {
                FireworksCelebrationOverlay(
                    onFinished = {
                        showFireworks = false
                        showDialog = true
                    },
                )
            }
        }
        if (showDialog && targetValueText != null && targetWeightKg != null) {
            WeighTargetMetDialog(
                targetWeightDisplay = targetValueText,
                onDismiss = {
                    showDialog = false
                    if (!archivedThisCycle) {
                        archivedThisCycle = true
                        val journey = journeyStartWeightKg ?: bodyWeightKg
                        vm.onWeightGoalMetDialogDismissed(
                            WeightGoalCompletionSnapshot(
                                targetWeightKg = targetWeightKg,
                                journeyStartWeightKg = journey,
                                achievedBodyWeightKg = bodyWeightKg,
                                completedAtEpochDay = LocalDate.now().toEpochDay(),
                                completedAtMs = System.currentTimeMillis(),
                            ),
                        )
                    }
                },
            )
        }
    }
}
