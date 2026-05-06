package com.weappsinc.watertracker.app.feature.weight.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.components.AppPrimaryButton
import com.weappsinc.watertracker.app.core.components.AppTopBar
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel.WeightViewModel
import com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel.WeightViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun WeightSelectionScreen(
    modifier: Modifier = Modifier,
    factory: WeightViewModelFactory,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val vm: WeightViewModel = viewModel(factory = factory)
    val selectedWeightKg by vm.weightKg.collectAsState()
    val weightValues = vm.displayRange().toList()
    val selectedDisplayWeight = vm.displayWeightValue()
    val listState = rememberLazyListState(weightValues.indexOf(selectedDisplayWeight).coerceAtLeast(0))
    val centerOffset = 2
    val currentSelectedIndex by remember(listState, weightValues) {
        derivedStateOf {
            val itemPx = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 1
            val adjust = if (listState.firstVisibleItemScrollOffset > itemPx / 2) 1 else 0
            (listState.firstVisibleItemIndex + centerOffset + adjust).coerceIn(0, weightValues.lastIndex)
        }
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .map { (index, offset) ->
                val itemPx = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 1
                val adjust = if (offset > itemPx / 2) 1 else 0
                weightValues[(index + centerOffset + adjust).coerceIn(0, weightValues.lastIndex)]
            }
            .distinctUntilChanged()
            .collectLatest { value ->
                vm.onSelectDisplayWeight(value)
            }
    }
    LaunchedEffect(Unit) {
        val topIndex = (weightValues.indexOf(selectedDisplayWeight).coerceAtLeast(0) - centerOffset).coerceAtLeast(0)
        if (listState.firstVisibleItemIndex != topIndex) listState.scrollToItem(topIndex)
    }
    Column(
        modifier = modifier.fillMaxSize().background(AppColors.GenderScreenBackground)
            .padding(horizontal = AppDimens.GenderScreenHorizontalPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            AppTopBar(onBack = onBack)
            Spacer(Modifier.height(AppDimens.AppBarTitleSpacing))
            Text(text = stringResource(R.string.weight_title), color = AppColors.GenderTitle, style = AppTypography.Title1)
            Spacer(Modifier.height(AppDimens.UnitToggleBottomSpacing))
        }
        Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimens.AgeWheelHeight),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(AppDimens.AgeItemHeight)
                        .background(AppColors.GenderUnselectedBackground, RoundedCornerShape(AppDimens.AgeHighlightCorner))
                )
                Box(Modifier.width(AppDimens.UnitColumnPrimaryWidth).height(AppDimens.AgeWheelHeight), contentAlignment = Alignment.Center) {
                    LazyColumn(
                        state = listState,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        userScrollEnabled = true
                    ) {
                        items(weightValues.size) { idx ->
                            val value = weightValues[idx]
                            val color = if (idx == currentSelectedIndex) AppColors.GenderPrimary else AppColors.GenderUnselectedContent
                            Text(
                                text = value.toString(),
                                color = color,
                                style = AppTypography.DisplayNumber,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().height(AppDimens.AgeItemHeight)
                            )
                        }
                    }
                }
            }
        }
        AppPrimaryButton(
            text = stringResource(R.string.next),
            onClick = { vm.saveSelection(); onNext() },
            modifier = Modifier.padding(bottom = AppDimens.AgeButtonBottomPadding)
        )
    }
}

