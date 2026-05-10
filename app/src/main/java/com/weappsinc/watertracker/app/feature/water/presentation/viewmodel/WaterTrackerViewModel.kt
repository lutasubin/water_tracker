package com.weappsinc.watertracker.app.feature.water.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weappsinc.watertracker.app.core.constants.WaterConstants
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterAppVisitRepository
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterIntakeRepository
import com.weappsinc.watertracker.app.feature.water.domain.model.WaterIntakeDisplayBaseline
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository
import com.weappsinc.watertracker.app.feature.water.domain.usecase.AddWaterIntakeUseCase
import com.weappsinc.watertracker.app.feature.water.presentation.mapper.WaterTrackerUiMapper
import com.weappsinc.watertracker.app.feature.water.presentation.state.WaterTrackerUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

@OptIn(ExperimentalCoroutinesApi::class)
class WaterTrackerViewModel(
    private val prefs: WaterPreferencesRepository,
    private val intake: WaterIntakeRepository,
    private val visits: WaterAppVisitRepository,
    private val addWaterIntake: AddWaterIntakeUseCase,
    private val zone: ZoneId = ZoneId.systemDefault()
) : ViewModel() {
    private var goalDoneDialogShownEpoch: Long = Long.MIN_VALUE

    init {
        viewModelScope.launch {
            prefs.observeGoalDoneDialogShownEpochDay().collect { storedEpoch ->
                goalDoneDialogShownEpoch = storedEpoch ?: Long.MIN_VALUE
            }
        }
    }

    val uiState = combine(
        prefs.observeFirstInstallEpochDay(),
        prefs.observeSavedGoalMl(),
        prefs.observeSavedUnit()
    ) { install, goal, unit -> Triple(install, goal, unit) }
        .flatMapLatest { (install, goal, unit) ->
            val today = LocalDate.now(zone)
            val monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toEpochDay()
            val sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toEpochDay()
            val first = install ?: today.toEpochDay()
            val low = minOf(monday, first)
            combine(
                intake.observeTotalsBetween(low, sunday),
                visits.observeOpenEpochDaysBetween(low, sunday),
                prefs.observeIntakeDisplayBaseline(),
            ) { map, openDays, baseline ->
                WaterTrackerUiMapper.buildState(
                    zone,
                    install,
                    goal,
                    unit,
                    map,
                    openDays,
                    java.util.Locale.getDefault(),
                    baseline,
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            WaterTrackerUiMapper.buildState(
                zone,
                null,
                null,
                null,
                emptyMap(),
                emptySet(),
                java.util.Locale.getDefault(),
                null,
            )
        )

    fun onDrink() {
        onDrink(WaterConstants.DEFAULT_DRINK_ML)
    }

    fun onDrink(amountMl: Int) {
        viewModelScope.launch {
            val today = LocalDate.now(zone).toEpochDay()
            addWaterIntake(today, amountMl.coerceAtLeast(0))
        }
    }

    /** Chỉ cho popup chúc mừng hiển thị 1 lần mỗi ngày. */
    fun shouldShowGoalDoneDialog(todayEpoch: Long, isGoalCompleted: Boolean): Boolean {
        if (!isGoalCompleted) return false
        return goalDoneDialogShownEpoch != todayEpoch
    }

    /** Đánh dấu popup đã hiển thị trong ngày hiện tại. */
    fun markGoalDoneDialogShown(todayEpoch: Long) {
        goalDoneDialogShownEpoch = todayEpoch
        viewModelScope.launch {
            prefs.saveGoalDoneDialogShownEpochDay(todayEpoch)
        }
    }

    /** Sau khi đóng popup đạt mục tiêu: UI tiến độ quay về 0 (session), dữ liệu tổng vẫn giữ cho báo cáo. */
    fun onGoalCompleteDialogDismissed(totalMlAtReset: Int) {
        viewModelScope.launch {
            val today = LocalDate.now(zone).toEpochDay()
            prefs.saveIntakeDisplayBaseline(
                WaterIntakeDisplayBaseline(today, totalMlAtReset.coerceAtLeast(0)),
            )
        }
    }
}

class WaterTrackerViewModelFactory(
    private val prefs: WaterPreferencesRepository,
    private val intake: WaterIntakeRepository,
    private val visits: WaterAppVisitRepository,
    private val addWaterIntake: AddWaterIntakeUseCase,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        WaterTrackerViewModel(prefs, intake, visits, addWaterIntake) as T
}
