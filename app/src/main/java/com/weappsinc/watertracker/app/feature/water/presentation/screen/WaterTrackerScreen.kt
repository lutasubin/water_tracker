package com.weappsinc.watertracker.app.feature.water.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.components.FireworksCelebrationOverlay
import com.weappsinc.watertracker.app.core.constants.WaterConstants
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit
import com.weappsinc.watertracker.app.feature.water.domain.util.WaterAmountFormat
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.DrinkAmountBottomSheet
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.GoalCompletedDialog
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.TrackerDrinkButton
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.WaterDrinkLottieOverlay
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.WaterGoalReminderCards
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.WaterProgressSection
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.WaterTrackerHeader
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.WeeklyReportSection
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModel
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModelFactory
import java.time.LocalDate

/**
 * Màn Water tab — map từng khối với design:
 * 1. [WaterTrackerHeader] — tiêu đề + pill streak
 * 2. [WaterProgressSection] — số đã uống (căn giữa) + thanh + “Tiến độ hôm nay” / % (xanh)
 * 3. [WaterGoalReminderCards] — [GoalStatCard] + [ReminderStatCard]
 * 4. [WeeklyReportSection] — card “Báo cáo” + 7 ngày
 * 5. [TrackerDrinkButton] — DRINK
 */
@Composable
fun WaterTrackerScreen(
    factory: WaterTrackerViewModelFactory,
    imageLoader: ImageLoader,
    onEditGoal: () -> Unit,
    onOpenReport: () -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: WaterTrackerViewModel = viewModel(factory = factory)
    val state by vm.uiState.collectAsState()
    val displayedIntakeMl = rememberDisplayIntakeMl(state.todayIntakeMl)
    var showGoalDoneDialog by remember { mutableStateOf(false) }
    var showGoalFireworks by remember { mutableStateOf(false) }
    var goalFireworksSession by remember { mutableIntStateOf(0) }
    var showDrinkSheet by remember { mutableStateOf(false) }
    var selectedDrinkMl by remember { mutableStateOf(WaterConstants.PRESET_DRINK_200) }
    var drinkLottieSession by remember { mutableIntStateOf(0) }
    var pendingDrinkMl by remember { mutableStateOf<Int?>(null) }

    val goalMl = state.goalMl
    val displayFraction =
        if (goalMl > 0) (displayedIntakeMl.toFloat() / goalMl).coerceIn(0f, 1f) else 0f
    val displayPercent = (displayFraction * 100f).toInt().coerceIn(0, 100)

    val unitMl = stringResource(R.string.unit_ml)
    val unitL = stringResource(R.string.unit_l)
    val goalDisplayCompact = when (state.unit) {
        WaterUnit.ML -> "${WaterAmountFormat.format(state.goalMl, state.unit)}$unitMl"
        WaterUnit.L -> "${WaterAmountFormat.format(state.goalMl, state.unit)} $unitL"
    }
    val isGoalCompleted = state.goalMl > 0 && displayedIntakeMl >= state.goalMl

    LaunchedEffect(isGoalCompleted) {
        val todayEpoch = LocalDate.now().toEpochDay()
        if (vm.shouldShowGoalDoneDialog(todayEpoch = todayEpoch, isGoalCompleted = isGoalCompleted)) {
            vm.markGoalDoneDialogShown(todayEpoch)
            goalFireworksSession++
            showGoalFireworks = true
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.HomeBackground)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = AppDimens.HomeHorizontalPadding)
        ) {
            WaterTrackerHeader(streakDays = state.streakDays)
            Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
            WaterProgressSection(
                intakeMlToday = displayedIntakeMl,
                displayUnit = state.unit,
                progressFraction = displayFraction,
                progressPercent = displayPercent,
                isGoalCompleted = isGoalCompleted
            )
            Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
            WaterGoalReminderCards(
                goalDisplayCompact = goalDisplayCompact,
                imageLoader = imageLoader,
                onEditGoal = onEditGoal
            )
            Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
            WeeklyReportSection(
                weekRings = state.weekRings,
                imageLoader = imageLoader,
                onOpenReport = onOpenReport
            )
            Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
            TrackerDrinkButton(
                onClick = { showDrinkSheet = true },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
        }
        if (showDrinkSheet) {
            DrinkAmountBottomSheet(
                amountMl = selectedDrinkMl,
                imageLoader = imageLoader,
                onDismiss = { showDrinkSheet = false },
                onDecrease = {
                    selectedDrinkMl = (selectedDrinkMl - WaterConstants.DRINK_STEP_ML)
                        .coerceAtLeast(WaterConstants.MIN_DRINK_ML)
                },
                onIncrease = {
                    selectedDrinkMl = (selectedDrinkMl + WaterConstants.DRINK_STEP_ML)
                        .coerceAtMost(WaterConstants.MAX_DRINK_ML)
                },
                onSelectPreset = { selectedDrinkMl = it },
                onDrink = {
                    val ml = selectedDrinkMl
                    showDrinkSheet = false
                    drinkLottieSession++
                    pendingDrinkMl = ml
                }
            )
        }
        if (showGoalDoneDialog) {
            GoalCompletedDialog(
                goalDisplayCompact = goalDisplayCompact,
                onDismiss = { showGoalDoneDialog = false }
            )
        }
        pendingDrinkMl?.let { ml ->
            WaterDrinkLottieOverlay(
                playToken = drinkLottieSession,
                onFinished = {
                    vm.onDrink(ml)
                    pendingDrinkMl = null
                }
            )
        }
        if (showGoalFireworks) {
            key(goalFireworksSession) {
                FireworksCelebrationOverlay(
                    onFinished = {
                        showGoalFireworks = false
                        showGoalDoneDialog = true
                    }
                )
            }
        }
    }
}
