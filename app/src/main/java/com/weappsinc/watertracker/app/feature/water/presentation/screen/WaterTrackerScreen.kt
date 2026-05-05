package com.weappsinc.watertracker.app.feature.water.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit
import com.weappsinc.watertracker.app.feature.water.domain.util.WaterAmountFormat
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.TrackerDrinkButton
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.WaterGoalReminderCards
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.WaterProgressSection
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.WaterTrackerHeader
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.WeeklyReportSection
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModel
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModelFactory

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
    modifier: Modifier = Modifier
) {
    val vm: WaterTrackerViewModel = viewModel(factory = factory)
    val state by vm.uiState.collectAsState()
    val displayedIntakeMl = rememberDisplayIntakeMl(state.todayIntakeMl)

    val goalMl = state.goalMl
    val displayFraction =
        if (goalMl > 0) (displayedIntakeMl.toFloat() / goalMl).coerceIn(0f, 1f) else 0f
    val displayPercent = (displayFraction * 100f).toInt().coerceIn(0, 100)

    val goalDisplayCompact = when (state.unit) {
        WaterUnit.ML -> "${WaterAmountFormat.format(state.goalMl, state.unit)}${AppText.UNIT_ML}"
        WaterUnit.L -> "${WaterAmountFormat.format(state.goalMl, state.unit)} ${AppText.UNIT_L}"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.HomeBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = AppDimens.HomeHorizontalPadding)
    ) {
        WaterTrackerHeader(streakDays = state.streakDays, imageLoader = imageLoader)
        Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
        WaterProgressSection(
            intakeMlToday = displayedIntakeMl,
            displayUnit = state.unit,
            progressFraction = displayFraction,
            progressPercent = displayPercent
        )
        Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
        WaterGoalReminderCards(
            goalDisplayCompact = goalDisplayCompact,
            imageLoader = imageLoader,
            onEditGoal = onEditGoal
        )
        Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
        WeeklyReportSection(weekRings = state.weekRings, imageLoader = imageLoader)
        Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
        TrackerDrinkButton(onClick = { vm.onDrink() })
        Spacer(Modifier.height(AppDimens.WaterTrackerBlockSpacing))
    }
}
