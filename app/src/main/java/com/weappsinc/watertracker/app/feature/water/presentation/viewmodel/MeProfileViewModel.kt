package com.weappsinc.watertracker.app.feature.water.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterIntakeRepository
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository
import com.weappsinc.watertracker.app.feature.water.presentation.mapper.MeProfileUiMapper
import com.weappsinc.watertracker.app.feature.water.presentation.state.MeProfileUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.ZoneId

/** ViewModel tab Me: tổng ml đã uống (từ ngày cài) + streak hiển thị giống Water Tracker. */
@OptIn(ExperimentalCoroutinesApi::class)
class MeProfileViewModel(
    private val prefs: WaterPreferencesRepository,
    private val intake: WaterIntakeRepository,
    private val zone: ZoneId = ZoneId.systemDefault(),
) : ViewModel() {

    val uiState = combine(
        prefs.observeFirstInstallEpochDay(),
        prefs.observeSavedGoalMl(),
    ) { install, goal -> install to goal }
        .flatMapLatest { (install, goal) ->
            val today = LocalDate.now(zone).toEpochDay()
            val start = (install ?: today).coerceAtMost(today)
            intake.observeTotalsBetween(start, today).map { totals ->
                MeProfileUiMapper.build(zone, install, goal, totals)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            MeProfileUiMapper.build(zone, null, null, emptyMap()),
        )
}
