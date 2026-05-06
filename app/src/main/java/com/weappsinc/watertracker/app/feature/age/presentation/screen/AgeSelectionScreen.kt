package com.weappsinc.watertracker.app.feature.age.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.components.AppPrimaryButton
import com.weappsinc.watertracker.app.core.components.AppTopBar
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.age.presentation.viewmodel.AgeViewModel
import com.weappsinc.watertracker.app.feature.age.presentation.viewmodel.AgeViewModelFactory
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AgeSelectionScreen(
    modifier: Modifier = Modifier,
    factory: AgeViewModelFactory,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val vm: AgeViewModel = viewModel(factory = factory)
    val selectedAge by vm.age.collectAsState()
    val ages = (10..80).toList()
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = ages.indexOf(selectedAge).coerceAtLeast(0))
    val centerOffset = 2

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collectLatest { (index, offset) ->
                val itemPx = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 1
                val adjust = if (offset > itemPx / 2) 1 else 0
                val selectedIndex = (index + centerOffset + adjust).coerceIn(0, ages.lastIndex)
                vm.onSelectAge(ages[selectedIndex])
            }
    }

    LaunchedEffect(selectedAge) {
        val ageIndex = ages.indexOf(selectedAge).coerceAtLeast(0)
        val topIndex = (ageIndex - centerOffset).coerceAtLeast(0)
        if (listState.firstVisibleItemIndex != topIndex) {
            listState.scrollToItem(topIndex)
        }
    }

    Column(
        modifier = modifier.fillMaxSize().background(AppColors.GenderScreenBackground)
            .padding(horizontal = AppDimens.GenderScreenHorizontalPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            AppTopBar(onBack = onBack)
            Spacer(Modifier.height(AppDimens.AppBarTitleSpacing))
            Text(
                text = stringResource(R.string.age_title),
                color = AppColors.GenderTitle,
                style = AppTypography.Title1
            )
            Spacer(Modifier.height(AppDimens.AgeTitleBottomSpacing))
        }
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier.fillMaxWidth().height(AppDimens.AgeWheelHeight), contentAlignment = Alignment.Center) {
                Box(Modifier.fillMaxWidth().height(AppDimens.AgeItemHeight).background(AppColors.GenderUnselectedBackground, RoundedCornerShape(AppDimens.AgeHighlightCorner)))
                LazyColumn(state = listState, horizontalAlignment = Alignment.CenterHorizontally) {
                    items(ages.size) { idx ->
                        val age = ages[idx]
                        val color = if (age == selectedAge) AppColors.GenderPrimary else AppColors.GenderUnselectedContent
                        Text(
                            text = age.toString(),
                            color = color,
                            style = AppTypography.DisplayNumber,
                            modifier = Modifier.height(AppDimens.AgeItemHeight)
                        )
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
