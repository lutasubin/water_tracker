package com.weappsinc.watertracker.app.feature.weigh.presentation.mapper

import com.weappsinc.watertracker.app.feature.weigh.domain.model.BmiCategory
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ComputeWeightProgressDeltaUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.util.BmiCalculator
import com.weappsinc.watertracker.app.feature.weigh.domain.util.BmiScaleGeometry
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay
import com.weappsinc.watertracker.app.feature.weigh.domain.util.WeighJourneyProgress
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighGoalDetailUiState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToInt

object WeighGoalDetailUiStateMapper {

    private val savedFmt: DateTimeFormatter =
        DateTimeFormatter.ofPattern("HH:mm dd/MM/uuuu").withZone(ZoneId.systemDefault())

    private const val DRAFT_MATCH_SAVED_TODAY_EPS = 0.05f

    @Suppress("LongParameterList")
    fun map(
        tallCm: Int,
        profileWeightKg: Int,
        unit: MassUnit,
        targetKg: Float?,
        journeyStartKg: Float?,
        latestTwo: List<WeighLogEntry>,
        todayLog: WeighLogEntry?,
        draftOverride: Float?,
        savedAtMs: Long?,
        recordError: Boolean,
        isRecording: Boolean,
        computeDelta: ComputeWeightProgressDeltaUseCase,
        classifyBmi: (Float) -> BmiCategory,
        mapBmiFraction: (Float) -> Float
    ): WeighGoalDetailUiState {
        val latest = latestTwo.firstOrNull()
        val beforeLatest = latestTwo.getOrNull(1)
        val baseDraft = (todayLog?.weightKg?.toFloat()
            ?: profileWeightKg.toFloat().takeIf { profileWeightKg > 0 }
            ?: 65f)
        val draft = draftOverride ?: baseDraft
        val startRef = journeyStartKg ?: profileWeightKg.toFloat().takeIf { profileWeightKg > 0 }
        val hasTarget = targetKg != null && targetKg > 0f
        val t = targetKg ?: 0f
        val gapAbs = if (hasTarget) abs(draft - t) else 0f
        val journeyStartForBar = startRef ?: draft
        val journeyFrac = if (hasTarget) {
            WeighJourneyProgress.fraction(journeyStartForBar, draft, t)
        } else {
            0f
        }
        val delta = computeDelta(
            draftKg = draft,
            latestLog = latest,
            beforeLatestLog = beforeLatest,
            fallbackStartKg = startRef
        )
        val isLossGoal = hasTarget && startRef != null && t < startRef
        val favorable = delta?.let { computeDelta.isDeltaFavorable(it, isLossGoal) } ?: true
        val neutral = delta == null || delta == 0f
        val deltaValueText = delta?.let { MassDisplay.formatSignedKgDelta(it, unit) } ?: "--"
        val hasDims = tallCm > 0 && draft > 0f
        val bmi = if (hasDims) BmiCalculator.computeBmi(tallCm, draft) else 0f
        val category = if (hasDims) classifyBmi(bmi) else BmiCategory.Normal
        val (segLow, segNorm) = BmiScaleGeometry.segmentThresholds()
        val savedTodayKg = todayLog?.weightKg?.toFloat()
        val draftMatchesTodayLog =
            savedTodayKg != null && abs(draft - savedTodayKg) < DRAFT_MATCH_SAVED_TODAY_EPS
        val showWeighRecordCta = todayLog == null || !draftMatchesTodayLog
        val savedBannerTime = if (!showWeighRecordCta && todayLog != null) {
            val ms = maxOf(todayLog.recordedAtMs, savedAtMs ?: 0L)
            savedFmt.format(Instant.ofEpochMilli(ms))
        } else {
            null
        }
        return WeighGoalDetailUiState(
            heightCm = tallCm,
            targetWeightKg = targetKg,
            displayMassUnit = unit,
            displayDraftKg = draft,
            targetValueText = if (hasTarget) MassDisplay.formatTargetKg(t, unit) else "--",
            gapValueText = if (hasTarget) MassDisplay.formatAbsKgDelta(gapAbs, unit) else "--",
            journeyProgressFraction = journeyFrac,
            journeyProgressPercent = (journeyFrac * 100f).roundToInt().coerceIn(0, 100),
            startWeightText = if (startRef != null) MassDisplay.formatTargetKg(startRef, unit) else "--",
            progressDeltaValueText = deltaValueText,
            progressDeltaFavorable = favorable,
            progressDeltaNeutral = neutral,
            bmiValueText = if (hasDims) String.format(Locale.US, "%.1f", bmi) else "--",
            bmiCategory = category,
            scaleLowEndFraction = segLow,
            scaleNormalEndFraction = segNorm,
            bmiIndicatorFraction = if (hasDims) mapBmiFraction(bmi) else 0.5f,
            showWeighRecordCta = showWeighRecordCta,
            savedBannerTime = savedBannerTime,
            lastRecordSuccessMs = savedAtMs,
            recordError = recordError,
            isRecording = isRecording
        )
    }
}
