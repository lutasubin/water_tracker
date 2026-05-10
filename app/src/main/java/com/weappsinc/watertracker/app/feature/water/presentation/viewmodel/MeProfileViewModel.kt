package com.weappsinc.watertracker.app.feature.water.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.age.domain.usecase.ObserveAgeUseCase
import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import com.weappsinc.watertracker.app.feature.gender.domain.usecase.ObserveSelectedGenderUseCase
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.ObserveTallUseCase
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterAppVisitRepository
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterIntakeRepository
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository
import com.weappsinc.watertracker.app.feature.water.presentation.mapper.MeProfileRowsMapper
import com.weappsinc.watertracker.app.feature.water.presentation.mapper.MeProfileUiMapper
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.ZoneId

private data class ProfileInputs(
    val tall: Int,
    val weightKg: Int,
    val age: Int,
    val gender: GenderType,
    val unit: MassUnit,
)

/** ViewModel tab Me: tổng ml đã uống (từ ngày cài) + streak theo ngày mở app + hàng hồ sơ. */
@OptIn(ExperimentalCoroutinesApi::class)
class MeProfileViewModel(
    private val prefs: WaterPreferencesRepository,
    private val intake: WaterIntakeRepository,
    private val visits: WaterAppVisitRepository,
    private val observeTall: ObserveTallUseCase,
    private val observeWeight: ObserveWeightUseCase,
    private val observeAge: ObserveAgeUseCase,
    private val observeSelectedGender: ObserveSelectedGenderUseCase,
    private val observeMassUnit: ObserveWeighMassUnitUseCase,
    private val zone: ZoneId = ZoneId.systemDefault(),
) : ViewModel() {

    val uiState = prefs.observeFirstInstallEpochDay().flatMapLatest { install ->
        val todayEpoch = LocalDate.now(zone).toEpochDay()
        val startEpoch = (install ?: todayEpoch).coerceAtMost(todayEpoch)
        combine(
            combine(
                intake.observeTotalsBetween(startEpoch, todayEpoch),
                visits.observeOpenEpochDaysBetween(startEpoch, todayEpoch),
            ) { totals, openDays -> Pair(totals, openDays) },
            combine(
                observeTall(),
                observeWeight(),
                observeAge(),
                observeSelectedGender(),
                observeMassUnit(),
            ) { tall, weightKg, age, gender, unit ->
                ProfileInputs(tall, weightKg, age, gender, unit)
            },
        ) { waterPair, profile ->
            val (totals, openDays) = waterPair
            val base = MeProfileUiMapper.build(zone, install, totals, openDays)
            val rows = MeProfileRowsMapper.map(
                profile.tall,
                profile.weightKg,
                profile.age,
                profile.gender,
                profile.unit,
            )
            base.copy(
                heightValueText = rows.heightValueText,
                weightValueText = rows.weightValueText,
                ageValueText = rows.ageValueText,
                sex = rows.sex,
                displayMassUnit = rows.displayMassUnit,
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MeProfileUiMapper.build(zone, null, emptyMap(), emptySet()),
    )
}
