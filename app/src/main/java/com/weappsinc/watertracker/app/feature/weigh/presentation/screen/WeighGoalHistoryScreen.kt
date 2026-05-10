package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighGoalHistoryUiState
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalHistoryViewModel
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalHistoryViewModelFactory

@Composable
fun WeighGoalHistoryScreen(
    factory: WeighGoalHistoryViewModelFactory,
    onBack: () -> Unit,
    onOpenGoalDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val vm: WeighGoalHistoryViewModel = viewModel(factory = factory)
    val state by vm.uiState.collectAsState()
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context).components { add(SvgDecoder.Factory()) }.build()
    }
    Column(
        modifier
            .fillMaxSize()
            .background(AppColors.HomeBackground),
    ) {
        AppTopBar(
            title = stringResource(R.string.weigh_goal_history_title),
            onBack = onBack,
            containerColor = AppColors.HomeBackground,
            contentColor = AppColors.HomeTitle,
            centerAligned = true,
            titleStyle = AppTypography.ReportTopBarTitle,
            matchParentHorizontalPadding = false,
        )
        when (val uiState = state) {
            WeighGoalHistoryUiState.Loading -> {
                Text(
                    text = stringResource(R.string.weigh_goal_history_loading),
                    color = AppColors.HomeMuted,
                    style = AppTypography.BodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = WeighDimens.ScreenHorizontalPadding)
                        .padding(top = AppDimens.ReportSectionSpacing),
                )
            }

            is WeighGoalHistoryUiState.Error,
            is WeighGoalHistoryUiState.Empty -> {
                Text(
                    text = stringResource(R.string.weigh_goal_history_empty),
                    color = AppColors.HomeMuted,
                    style = AppTypography.BodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = WeighDimens.ScreenHorizontalPadding)
                        .padding(top = AppDimens.ReportSectionSpacing),
                )
            }

            is WeighGoalHistoryUiState.Content -> {
                LazyColumn(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = WeighDimens.ScreenHorizontalPadding),
                ) {
                    items(uiState.rows, key = { it.id }) { row ->
                        WeighGoalHistoryListItem(
                            row = row,
                            unit = uiState.displayUnit,
                            imageLoader = imageLoader,
                            onClick = onOpenGoalDetail,
                        )
                        Spacer(Modifier.height(WeighDimens.TargetSectionTopSpacing))
                    }
                }
            }
        }
    }
}
