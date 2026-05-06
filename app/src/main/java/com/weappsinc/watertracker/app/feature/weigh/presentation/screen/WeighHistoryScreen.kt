package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighHistoryViewModel
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighHistoryViewModelFactory

@Composable
fun WeighHistoryScreen(
    factory: WeighHistoryViewModelFactory,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: WeighHistoryViewModel = viewModel(factory = factory)
    val state by vm.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.HomeBackground)
    ) {
        Spacer(Modifier.padding(top = AppDimens.HomeSectionSpacing))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = WeighDimens.ScreenHorizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back),
                    tint = AppColors.HomeTitle
                )
            }
            Text(
                text = stringResource(R.string.weigh_history_title),
                modifier = Modifier.weight(1f),
                style = AppTypography.Title2,
                color = AppColors.HomeTitle,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.width(48.dp))
        }
        Spacer(Modifier.padding(top = 12.dp))
        LazyColumn(
            contentPadding = PaddingValues(
                start = WeighDimens.ScreenHorizontalPadding,
                end = WeighDimens.ScreenHorizontalPadding,
                bottom = AppDimens.HomeBottomNavHeight
            )
        ) {
            item {
                WeighHistoryTrendSection(
                    chartPoints = state.chartPoints,
                    xLabels = state.xLabels
                )
            }
            items(state.listRows) { row ->
                WeighHistoryLogRow(row, state.massUnit, Modifier.padding(top = 12.dp))
            }
        }
    }
}
