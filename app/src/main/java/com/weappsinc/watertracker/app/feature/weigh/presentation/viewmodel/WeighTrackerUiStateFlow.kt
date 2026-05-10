package com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel

import com.weappsinc.watertracker.app.feature.tall.domain.usecase.ObserveTallUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ClassifyBmiUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.MapBmiToScaleFractionUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighJourneyStartWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLatestLogUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighTargetWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.presentation.mapper.WeighTrackerUiStateMapper
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

private data class WeighTrackerBaseInputs(
    val tall: Int,
    val profileKg: Int,
    val unit: MassUnit,
    val target: Float?,
    val journeyStart: Float?,
)

/** Tách combine state — giữ [WeighTrackerViewModel] gọn. */
internal fun buildWeighTrackerUiStateFlow(
    scope: CoroutineScope,
    observeTall: ObserveTallUseCase,
    observeWeight: ObserveWeightUseCase,
    observeLatestLog: ObserveWeighLatestLogUseCase,
    observeMassUnit: ObserveWeighMassUnitUseCase,
    observeTargetWeightKg: ObserveWeighTargetWeightKgUseCase,
    observeJourneyStartWeightKg: ObserveWeighJourneyStartWeightKgUseCase,
    classifyBmi: ClassifyBmiUseCase,
    mapBmiFraction: MapBmiToScaleFractionUseCase,
) = combine(
    combine(
        observeTall(),
        observeWeight(),
        observeMassUnit(),
        observeTargetWeightKg(),
        observeJourneyStartWeightKg(),
    ) { tall, weight, unit, target, journeyStart ->
        WeighTrackerBaseInputs(tall, weight, unit, target, journeyStart)
    },
    observeLatestLog(),
) { base, latest ->
    WeighTrackerUiStateMapper.map(
        tallCm = base.tall,
        profileWeightKg = base.profileKg,
        unit = base.unit,
        targetKg = base.target,
        journeyStartKg = base.journeyStart,
        latestLog = latest,
        classifyBmi = classifyBmi::invoke,
        mapBmiFraction = mapBmiFraction::invoke,
    )
}.stateIn(
    scope,
    SharingStarted.Lazily,
    WeighTrackerUiStateMapper.map(
        0,
        0,
        MassUnit.KG,
        null,
        null,
        null,
        classifyBmi::invoke,
        mapBmiFraction::invoke,
    ),
)
