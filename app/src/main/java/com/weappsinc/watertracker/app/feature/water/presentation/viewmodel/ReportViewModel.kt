package com.weappsinc.watertracker.app.feature.water.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterIntakeRepository
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository
import com.weappsinc.watertracker.app.feature.water.domain.usecase.BuildDayChartBucketsFromLogsUseCase
import com.weappsinc.watertracker.app.feature.water.presentation.mapper.ReportUiMapper
import com.weappsinc.watertracker.app.feature.water.presentation.mapper.ReportWeekMonthMapper
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportPeriod
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalCoroutinesApi::class)
class ReportViewModel(
    private val prefs: WaterPreferencesRepository,
    private val intake: WaterIntakeRepository,
    private val buildDayBuckets: BuildDayChartBucketsFromLogsUseCase,
    private val zone: ZoneId = ZoneId.systemDefault()
) : ViewModel() {

    private val _period = MutableStateFlow(ReportPeriod.Day)
    private val _anchor = MutableStateFlow(LocalDate.now(zone))
    private val _monthWindowIndex = MutableStateFlow(0)
    private val _selectedBar = MutableStateFlow<Int?>(null)

    private data class ReportNavInputs(
        val period: ReportPeriod,
        val anchor: LocalDate,
        val monthWindowIndex: Int,
        val selectedBar: Int?
    )

    private val reportNav = combine(
        _period,
        _anchor,
        _monthWindowIndex,
        _selectedBar
    ) { p, a, mi, sb ->
        ReportNavInputs(p, a, mi, sb)
    }

    val uiState = combine(
        reportNav,
        prefs.observeSavedGoalMl(),
        prefs.observeFirstInstallEpochDay()
    ) { nav, g, installEp ->
        ReportParams(
            nav.period,
            nav.anchor,
            nav.monthWindowIndex,
            nav.selectedBar,
            g?.coerceAtLeast(0) ?: 0,
            installEp
        )
    }
        .flatMapLatest { p ->
            when (p.period) {
                ReportPeriod.Day -> combine(
                    intake.observeLogsForDay(p.anchor.toEpochDay()),
                    intake.observeTotalMlForDay(p.anchor.toEpochDay())
                ) { logs, total ->
                    val buckets = buildDayBuckets(logs, zone)
                    ReportUiMapper.buildDayState(
                        p.anchor, p.goalMl, total, logs, buckets, zone, p.selectedBar
                    )
                }
                ReportPeriod.Week -> {
                    val (lo, hi) = ReportUiMapper.weekEpochRange(p.anchor)
                    intake.observeTotalsBetween(lo, hi).map { m ->
                        ReportWeekMonthMapper.buildWeekState(p.anchor, p.goalMl, m, p.selectedBar)
                    }
                }
                ReportPeriod.Month -> {
                    val today = LocalDate.now(zone)
                    val install = LocalDate.ofEpochDay(p.installEpoch ?: today.toEpochDay())
                    val maxIdx = ReportWeekMonthMapper.maxMonthWindowIndex(today, install)
                    val idx = p.monthWindowIndex.coerceIn(0, maxIdx)
                    val windowEnd = today.minusDays(7L * idx)
                    val (lo, hi) = ReportUiMapper.sevenDayWindowEpochRange(windowEnd)
                    intake.observeTotalsBetween(lo, hi).map { m ->
                        ReportWeekMonthMapper.buildSevenDayWindowState(
                            windowEnd = windowEnd,
                            goalMl = p.goalMl,
                            totalsByDay = m,
                            selectedBarIndex = p.selectedBar,
                            windowIndex = idx,
                            maxWindowIndex = maxIdx
                        )
                    }
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ReportUiMapper.buildDayState(
                LocalDate.now(zone), 0, 0, emptyList(),
                List(6) { 0 }, zone, null
            )
        )

    fun onPeriodChange(period: ReportPeriod) {
        _period.value = period
        _selectedBar.value = null
        if (period == ReportPeriod.Month) {
            _monthWindowIndex.value = 0
        }
    }

    fun onBarSelected(index: Int?) {
        _selectedBar.value = index
    }

    fun onPrevRange() {
        when (_period.value) {
            ReportPeriod.Day -> _anchor.value = _anchor.value.minusDays(1)
            ReportPeriod.Week -> _anchor.value = _anchor.value.minusWeeks(1)
            ReportPeriod.Month -> viewModelScope.launch { shiftMonthWindowOlder() }
        }
        _selectedBar.value = null
    }

    fun onNextRange() {
        when (_period.value) {
            ReportPeriod.Day -> _anchor.value = _anchor.value.plusDays(1)
            ReportPeriod.Week -> _anchor.value = _anchor.value.plusWeeks(1)
            ReportPeriod.Month -> _monthWindowIndex.update { (it - 1).coerceAtLeast(0) }
        }
        _selectedBar.value = null
    }

    private suspend fun shiftMonthWindowOlder() {
        val today = LocalDate.now(zone)
        val inst = prefs.observeFirstInstallEpochDay().first()
        val install = LocalDate.ofEpochDay(inst ?: today.toEpochDay())
        val maxIdx = ReportWeekMonthMapper.maxMonthWindowIndex(today, install)
        _monthWindowIndex.update { (it + 1).coerceAtMost(maxIdx) }
    }

    private data class ReportParams(
        val period: ReportPeriod,
        val anchor: LocalDate,
        val monthWindowIndex: Int,
        val selectedBar: Int?,
        val goalMl: Int,
        val installEpoch: Long?
    )
}

class ReportViewModelFactory(
    private val prefs: WaterPreferencesRepository,
    private val intake: WaterIntakeRepository,
    private val buildDayBuckets: BuildDayChartBucketsFromLogsUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ReportViewModel(prefs, intake, buildDayBuckets) as T
}
