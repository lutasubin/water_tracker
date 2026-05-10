package com.weappsinc.watertracker.app.feature.water.presentation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weappsinc.watertracker.app.core.components.AppTopBar
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportPeriod
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.ReportViewModel
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.ReportViewModelFactory

@Composable
fun ReportScreen(
    factory: ReportViewModelFactory,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: ReportViewModel = viewModel(factory = factory)
    val state by vm.uiState.collectAsState()
    Column(modifier.fillMaxSize().background(AppColors.HomeBackground)) {
        AppTopBar(
            title = stringResource(R.string.report_screen_title),
            onBack = onBack,
            containerColor = AppColors.HomeBackground,
            contentColor = AppColors.HomeTitle,
            centerAligned = true,
            titleStyle = AppTypography.ReportTopBarTitle
        )
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(AppDimens.ReportSectionSpacing))
            ReportPeriodTabs(selected = state.period, onSelect = vm::onPeriodChange)
            Spacer(Modifier.height(AppDimens.ReportSectionSpacing))
            ReportSummaryCards(state)
            Spacer(Modifier.height(AppDimens.ReportSectionSpacing))
            ReportChartSection(
                state = state,
                onPrev = vm::onPrevRange,
                onNext = vm::onNextRange,
                onBarClick = { idx ->
                    vm.onBarSelected(if (state.selectedBarIndex == idx) null else idx)
                }
            )
            if (state.period == ReportPeriod.Day && state.records.isNotEmpty()) {
                Spacer(Modifier.height(AppDimens.ReportSectionSpacing))
                ReportRecordsList(records = state.records)
            }
            Spacer(Modifier.height(AppDimens.ReportSectionSpacing))
        }
    }
}
