package com.weappsinc.watertracker.app.feature.tall.presentation.screen

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
import com.weappsinc.watertracker.app.feature.tall.presentation.viewmodel.TallViewModel
import com.weappsinc.watertracker.app.feature.tall.presentation.viewmodel.TallViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun TallSelectionScreen(
    modifier: Modifier = Modifier,
    factory: TallViewModelFactory,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val vm: TallViewModel = viewModel(factory = factory)
    val selectedTall by vm.tallCm.collectAsState()
    val cmValues = vm.displayRange().toList()
    val selectedValue = vm.displayTallValue()
    val cmListState = rememberLazyListState(cmValues.indexOf(selectedValue).coerceAtLeast(0))
    val centerOffset = 2
    val currentSelectedIndex by remember(cmListState, cmValues) {
        derivedStateOf {
            val itemPx = cmListState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 1
            val adjust = if (cmListState.firstVisibleItemScrollOffset > itemPx / 2) 1 else 0
            (cmListState.firstVisibleItemIndex + centerOffset + adjust).coerceIn(0, cmValues.lastIndex)
        }
    }
    LaunchedEffect(cmListState) {
        snapshotFlow { cmListState.firstVisibleItemIndex to cmListState.firstVisibleItemScrollOffset }
            .map { (index, offset) ->
                val itemPx = cmListState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 1
                val adjust = if (offset > itemPx / 2) 1 else 0
                cmValues[(index + centerOffset + adjust).coerceIn(0, cmValues.lastIndex)]
            }
            .distinctUntilChanged()
            .collectLatest { value ->
                vm.onSelectDisplayTall(value)
            }
    }
    LaunchedEffect(Unit) {
        val cmTop = (cmValues.indexOf(selectedValue).coerceAtLeast(0) - centerOffset).coerceAtLeast(0)
        if (cmListState.firstVisibleItemIndex != cmTop) cmListState.scrollToItem(cmTop)
    }
    Column(
        modifier = modifier.fillMaxSize().background(AppColors.GenderScreenBackground)
            .padding(horizontal = AppDimens.GenderScreenHorizontalPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            AppTopBar(onBack = onBack)
            Spacer(Modifier.height(AppDimens.AppBarTitleSpacing))
            Text(text = stringResource(R.string.tall_title), color = AppColors.GenderTitle, style = AppTypography.Title1)
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
                NumberWheel(
                    values = cmValues,
                    selectedIndex = currentSelectedIndex,
                    state = cmListState,
                    userScrollEnabled = true,
                    modifier = Modifier.width(AppDimens.UnitColumnPrimaryWidth)
                )
            }
        }
        AppPrimaryButton(
            text = stringResource(R.string.next),
            onClick = { vm.saveSelection(); onNext() },
            modifier = Modifier.padding(bottom = AppDimens.AgeButtonBottomPadding)
        )
    }
}

@Composable
private fun NumberWheel(
    values: List<Int>,
    selectedIndex: Int,
    state: androidx.compose.foundation.lazy.LazyListState,
    userScrollEnabled: Boolean,
    modifier: Modifier
) {
    Box(modifier.height(AppDimens.AgeWheelHeight), contentAlignment = Alignment.Center) {
        LazyColumn(
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = userScrollEnabled
        ) {
            items(values.size) { idx ->
                val value = values[idx]
                val color = if (idx == selectedIndex) AppColors.GenderPrimary else AppColors.GenderUnselectedContent
                Text(
                    text = value.toString(),
                    color = color,
                    style = AppTypography.DisplayNumber,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppDimens.AgeItemHeight)
                )
            }
        }
    }
}

