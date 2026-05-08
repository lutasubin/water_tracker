package com.weappsinc.watertracker.app.feature.water.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterAppVisitRepository
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterIntakeRepository
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository
import com.weappsinc.watertracker.app.feature.water.presentation.mapper.MeProfileUiMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.ZoneId

/** ViewModel tab Me: tổng ml đã uống (từ ngày cài) + streak theo ngày mở app. */
@OptIn(ExperimentalCoroutinesApi::class)
class MeProfileViewModel(
    private val prefs: WaterPreferencesRepository,
    private val intake: WaterIntakeRepository,
    private val visits: WaterAppVisitRepository,
    private val zone: ZoneId = ZoneId.systemDefault(),
) : ViewModel() {

    val uiState = prefs.observeFirstInstallEpochDay().flatMapLatest { install ->
        val todayEpoch = LocalDate.now(zone).toEpochDay()
        val startEpoch = (install ?: todayEpoch).coerceAtMost(todayEpoch)
        combine(
            intake.observeTotalsBetween(startEpoch, todayEpoch),
            visits.observeOpenEpochDaysBetween(startEpoch, todayEpoch),
        ) { totals, openDays ->
            MeProfileUiMapper.build(zone, install, totals, openDays)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MeProfileUiMapper.build(zone, null, emptyMap(), emptySet()),
    )
}
