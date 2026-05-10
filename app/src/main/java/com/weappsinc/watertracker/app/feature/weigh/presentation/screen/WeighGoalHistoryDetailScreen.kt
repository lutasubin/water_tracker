package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.components.AppTopBar
import com.weappsinc.watertracker.app.core.components.massUnitShortLabel
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighGoalHistoryDetailUiState
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalHistoryDetailViewModel
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalHistoryDetailViewModelFactory

@Composable
fun WeighGoalHistoryDetailScreen(
    factory: WeighGoalHistoryDetailViewModelFactory,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val vm: WeighGoalHistoryDetailViewModel = viewModel(factory = factory)
    val state by vm.uiState.collectAsState()
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context).components { add(SvgDecoder.Factory()) }.build()
    }
    Column(modifier.fillMaxSize().background(AppColors.HomeBackground)) {
        AppTopBar(
            title = stringResource(R.string.weigh_goal_history_detail_title),
            onBack = onBack,
            containerColor = AppColors.HomeBackground,
            contentColor = AppColors.HomeTitle,
            centerAligned = true,
            titleStyle = AppTypography.ReportTopBarTitle,
            matchParentHorizontalPadding = false,
        )
        when (val uiState = state) {
            WeighGoalHistoryDetailUiState.Loading -> {
                Text(
                    text = stringResource(R.string.weigh_goal_history_loading),
                    style = AppTypography.BodyMedium,
                    color = AppColors.HomeMuted,
                    modifier = Modifier.padding(horizontal = WeighDimens.ScreenHorizontalPadding),
                )
            }

            is WeighGoalHistoryDetailUiState.Error,
            is WeighGoalHistoryDetailUiState.Empty -> {
                Text(
                    text = stringResource(R.string.weigh_goal_history_empty),
                    style = AppTypography.BodyMedium,
                    color = AppColors.HomeMuted,
                    modifier = Modifier.padding(horizontal = WeighDimens.ScreenHorizontalPadding),
                )
            }

            is WeighGoalHistoryDetailUiState.Content -> {
                val massUnitLabel = massUnitShortLabel(uiState.displayUnit)
                Column(Modifier.padding(horizontal = WeighDimens.ScreenHorizontalPadding)) {
                    WeighGoalCard(
                        targetValueText = uiState.targetValueText,
                        massUnitLabel = massUnitLabel,
                        gapValueText = uiState.progressDeltaValueText,
                        journeyProgressFraction = 1f,
                        journeyProgressPercent = 100,
                        imageLoader = imageLoader,
                        onClick = null,
                    )
                    Spacer(Modifier.padding(top = WeighDimens.TargetSectionTopSpacing))
                    Text(
                        text = stringResource(
                            R.string.weigh_goal_history_achieved_format,
                            uiState.achievedValueText,
                            massUnitLabel,
                            uiState.completedAtText,
                        ),
                        style = AppTypography.BodyMedium,
                        color = AppColors.HomeMuted,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.padding(top = WeighDimens.TargetSectionTopSpacing))
                    WeighGoalDetailStatsRow(
                        startWeightText = uiState.startWeightText,
                        massUnitLabel = massUnitLabel,
                        progressDeltaValueText = uiState.progressDeltaValueText,
                        progressDeltaFavorable = uiState.progressDeltaFavorable,
                        progressDeltaNeutral = uiState.progressDeltaNeutral,
                    )
                    Spacer(Modifier.padding(top = WeighDimens.TargetSectionTopSpacing))
                    WeighHistoryTrendSection(
                        chartPoints = uiState.chartPoints,
                        xLabels = uiState.xLabels,
                    )
                }
            }
        }
    }
}
