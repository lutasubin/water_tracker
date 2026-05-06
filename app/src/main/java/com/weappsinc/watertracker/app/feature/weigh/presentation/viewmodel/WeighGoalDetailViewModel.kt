package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.ObserveTallUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ClassifyBmiUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ComputeWeightProgressDeltaUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.MapBmiToScaleFractionUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighJourneyStartWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLatestLogForTodayUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLatestTwoLogsUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighTargetWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighLogUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighTargetWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay
import com.weappsinc.watertracker.app.feature.weigh.presentation.mapper.WeighGoalDetailUiStateMapper
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.SaveWeightUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class WeighGoalDetailViewModel(
    private val observeTall: ObserveTallUseCase,
    private val observeWeight: ObserveWeightUseCase,
    private val observeMassUnit: ObserveWeighMassUnitUseCase,
    private val saveMassUnit: SaveWeighMassUnitUseCase,
    private val observeTargetWeightKg: ObserveWeighTargetWeightKgUseCase,
    private val saveTargetWeightKg: SaveWeighTargetWeightKgUseCase,
    private val observeJourneyStartWeightKg: ObserveWeighJourneyStartWeightKgUseCase,
    private val observeLatestTwoLogs: ObserveWeighLatestTwoLogsUseCase,
    private val observeLatestLogForToday: ObserveWeighLatestLogForTodayUseCase,
    private val saveWeighLog: SaveWeighLogUseCase,
    private val saveWeightProfile: SaveWeightUseCase,
    private val computeDelta: ComputeWeightProgressDeltaUseCase,
    private val classifyBmi: ClassifyBmiUseCase,
    private val mapBmiFraction: MapBmiToScaleFractionUseCase
) : ViewModel() {

    private val draftOverride = MutableStateFlow<Float?>(null)
    private val savedAtMs = MutableStateFlow<Long?>(null)
    private val recordError = MutableStateFlow<String?>(null)
    private val isRecording = MutableStateFlow(false)

    private val profileInputs = combine(
        observeTall(),
        observeWeight(),
        observeMassUnit(),
        observeTargetWeightKg(),
        observeJourneyStartWeightKg()
    ) { tall, w, unit, target, journey ->
        WeighGoalDetailProfileInputs(tall, w, unit, target, journey)
    }

    private val coreFlow = combine(
        profileInputs,
        observeLatestTwoLogs(),
        observeLatestLogForToday()
    ) { prof, two, today ->
        WeighGoalDetailCore(
            tall = prof.tall,
            profile = prof.profile,
            unit = prof.unit,
            target = prof.target,
            journey = prof.journey,
            latestTwo = two,
            todayLog = today
        )
    }

    val uiState = combine(
        coreFlow,
        draftOverride,
        savedAtMs,
        recordError,
        isRecording
    ) { core, draftO, saved, err, rec ->
        WeighGoalDetailUiStateMapper.map(
            tallCm = core.tall,
            profileWeightKg = core.profile,
            unit = core.unit,
            targetKg = core.target,
            journeyStartKg = core.journey,
            latestTwo = core.latestTwo,
            todayLog = core.todayLog,
            draftOverride = draftO,
            savedAtMs = saved,
            recordError = err,
            isRecording = rec,
            computeDelta = computeDelta,
            classifyBmi = classifyBmi::invoke,
            mapBmiFraction = mapBmiFraction::invoke
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        WeighGoalDetailUiStateMapper.map(
            0, 0, MassUnit.KG, null, null,
            emptyList(), null, null, null, null, false,
            computeDelta, classifyBmi::invoke, mapBmiFraction::invoke
        )
    )

    fun onMassUnitSelected(unit: MassUnit) {
        viewModelScope.launch { saveMassUnit(unit) }
    }

    fun onDraftStep(stepKg: Float) {
        recordError.value = null
        val cur = draftOverride.value ?: uiState.value.displayDraftKg
        draftOverride.value =
            MassDisplay.snapTargetKg(cur + stepKg).coerceIn(30f, 250f)
    }

    /** Lưu log + đồng bộ cân hồ sơ. */
    fun onRecordWeight(kg: Float) {
        viewModelScope.launch {
            isRecording.value = true
            recordError.value = null
            try {
                saveWeighLog(kg).getOrThrow()
                saveWeightProfile(MassDisplay.snapTargetKg(kg).roundToInt())
                savedAtMs.value = System.currentTimeMillis()
                draftOverride.value = null
            } catch (_: Exception) {
                recordError.value = AppText.WEIGH_GOAL_DETAIL_RECORD_ERROR
            } finally {
                isRecording.value = false
            }
        }
    }

    /** Chỉ cập nhật mục tiêu (màn chi tiết — không reset mốc hành trình). */
    fun onSaveTargetOnly(targetKg: Float) {
        viewModelScope.launch {
            saveTargetWeightKg(MassDisplay.snapTargetKg(targetKg))
        }
    }

    fun clearRecordError() {
        recordError.value = null
    }

    private data class WeighGoalDetailProfileInputs(
        val tall: Int,
        val profile: Int,
        val unit: MassUnit,
        val target: Float?,
        val journey: Float?
    )

    private data class WeighGoalDetailCore(
        val tall: Int,
        val profile: Int,
        val unit: MassUnit,
        val target: Float?,
        val journey: Float?,
        val latestTwo: List<WeighLogEntry>,
        val todayLog: WeighLogEntry?
    )
}
