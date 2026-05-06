package com.weappsinc.watertracker.app.feature.weigh.presentation.mapper

import com.weappsinc.watertracker.app.feature.weigh.domain.model.BmiCategory
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.util.BmiCalculator
import com.weappsinc.watertracker.app.feature.weigh.domain.util.BmiScaleGeometry
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay
import com.weappsinc.watertracker.app.feature.weigh.domain.util.WeighJourneyProgress
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighTrackerUiState
import java.util.Locale
import kotlin.math.roundToInt

/** Gom dữ liệu domain → một [WeighTrackerUiState]. */
object WeighTrackerUiStateMapper {

    fun map(
        tallCm: Int,
        profileWeightKg: Int,
        unit: MassUnit,
        targetKg: Float?,
        journeyStartKg: Float?,
        latestLog: WeighLogEntry?,
        classifyBmi: (Float) -> BmiCategory,
        mapBmiFraction: (Float) -> Float
    ): WeighTrackerUiState {
        val bodyKg = latestLog?.weightKg?.toFloat()
            ?: profileWeightKg.takeIf { it > 0 }?.toFloat()
            ?: 0f
        val hasDims = tallCm > 0 && bodyKg > 0f
        val bmi = if (hasDims) BmiCalculator.computeBmi(tallCm, bodyKg) else 0f
        val category = if (hasDims) classifyBmi(bmi) else BmiCategory.Normal
        val (segLow, segNorm) = BmiScaleGeometry.segmentThresholds()
        val hasTarget = targetKg != null && targetKg > 0f
        val t = targetKg ?: 0f
        val gapAbs = if (hasTarget && bodyKg > 0f) kotlin.math.abs(bodyKg - t) else 0f
        val journeyFrac = if (hasTarget && bodyKg > 0f) {
            val start = journeyStartKg ?: bodyKg
            WeighJourneyProgress.fraction(start, bodyKg, t)
        } else {
            0f
        }
        return WeighTrackerUiState(
            heightCm = tallCm,
            bodyWeightKg = bodyKg,
            heightValueText = if (tallCm > 0) tallCm.toString() else "--",
            weightValueText = if (bodyKg > 0f) MassDisplay.formatTargetKg(bodyKg, unit) else "--",
            displayMassUnit = unit,
            bmiValueText = if (hasDims) String.format(Locale.US, "%.1f", bmi) else "--",
            bmiCategory = category,
            scaleLowEndFraction = segLow,
            scaleNormalEndFraction = segNorm,
            bmiIndicatorFraction = if (hasDims) mapBmiFraction(bmi) else 0.5f,
            targetWeightKg = targetKg,
            targetValueText = targetKg?.let { MassDisplay.formatTargetKg(it, unit) },
            gapValueText = MassDisplay.formatAbsKgDelta(gapAbs, unit),
            journeyProgressFraction = journeyFrac,
            journeyProgressPercent = (journeyFrac * 100f).roundToInt().coerceIn(0, 100)
        )
    }
}
