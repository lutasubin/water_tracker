package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.ObserveTallUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ClassifyBmiUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.MapBmiToScaleFractionUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighJourneyStartWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLatestLogUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighTargetWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighJourneyStartWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighTargetWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay
import com.weappsinc.watertracker.app.feature.weigh.presentation.mapper.WeighTrackerUiStateMapper
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private data class WeighTrackerBaseInputs(
    val tall: Int,
    val profileKg: Int,
    val unit: MassUnit,
    val target: Float?,
    val journeyStart: Float?
)

class WeighTrackerViewModel(
    private val observeTall: ObserveTallUseCase,
    private val observeWeight: ObserveWeightUseCase,
    private val observeLatestLog: ObserveWeighLatestLogUseCase,
    private val observeMassUnit: ObserveWeighMassUnitUseCase,
    private val saveMassUnit: SaveWeighMassUnitUseCase,
    private val observeTargetWeightKg: ObserveWeighTargetWeightKgUseCase,
    private val saveTargetWeightKg: SaveWeighTargetWeightKgUseCase,
    private val observeJourneyStartWeightKg: ObserveWeighJourneyStartWeightKgUseCase,
    private val saveJourneyStartWeightKg: SaveWeighJourneyStartWeightKgUseCase,
    private val classifyBmi: ClassifyBmiUseCase,
    private val mapBmiFraction: MapBmiToScaleFractionUseCase
) : ViewModel() {

    val uiState = combine(
        combine(
            observeTall(),
            observeWeight(),
            observeMassUnit(),
            observeTargetWeightKg(),
            observeJourneyStartWeightKg()
        ) { tall, weight, unit, target, journeyStart ->
            WeighTrackerBaseInputs(tall, weight, unit, target, journeyStart)
        },
        observeLatestLog()
    ) { base, latest ->
        WeighTrackerUiStateMapper.map(
            tallCm = base.tall,
            profileWeightKg = base.profileKg,
            unit = base.unit,
            targetKg = base.target,
            journeyStartKg = base.journeyStart,
            latestLog = latest,
            classifyBmi = classifyBmi::invoke,
            mapBmiFraction = mapBmiFraction::invoke
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        WeighTrackerUiStateMapper.map(
            0,
            0,
            MassUnit.KG,
            null,
            null,
            null,
            classifyBmi::invoke,
            mapBmiFraction::invoke
        )
    )

    fun onMassUnitSelected(unit: MassUnit) {
        viewModelScope.launch { saveMassUnit(unit) }
    }

    /** Lưu mục tiêu + mốc bắt đầu hành trình (cân hiện tại khi bấm CTA). */
    fun onConfirmTargetJourney(targetKg: Float, currentBodyWeightKg: Float) {
        viewModelScope.launch {
            val t = MassDisplay.snapTargetKg(targetKg)
            saveTargetWeightKg(t)
            saveJourneyStartWeightKg(MassDisplay.snapTargetKg(currentBodyWeightKg))
        }
    }
}
