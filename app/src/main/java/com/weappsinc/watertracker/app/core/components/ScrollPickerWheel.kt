@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package com.weappsinc.watertracker.app.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Vòng số dọc: snap vuốt + bấm dòng căn đúng số vào giữa vạch; vùng cuộn ngang = [modifier] (đủ rộng để vuốt tay lệch).
 */
@Composable
fun ScrollPickerWheel(
    values: List<Int>,
    selectedValue: Int,
    onSelectedChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    wheelHeight: Dp = AppDimens.AgeWheelHeight,
    itemHeight: Dp = AppDimens.AgeItemHeight,
) {
    val scope = rememberCoroutineScope()
    val initialIndex = values.indexOf(selectedValue).takeIf { it >= 0 } ?: 0
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val snapFling = rememberSnapFlingBehavior(lazyListState = listState)
    val inset = (wheelHeight - itemHeight) / 2
    val wheelNumberStyle = remember {
        AppTypography.DisplayNumber.copy(
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.Both,
            ),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        )
    }

    val centerRowIndex by remember(listState, values.size) {
        derivedStateOf {
            centeredIndex(listState, values.lastIndex)
        }
    }

    LaunchedEffect(selectedValue, values) {
        val idx = values.indexOf(selectedValue).takeIf { it >= 0 } ?: 0
        if (listState.firstVisibleItemIndex != idx || listState.firstVisibleItemScrollOffset != 0) {
            listState.scrollToItem(idx)
        }
    }

    LaunchedEffect(listState, values) {
        snapshotFlow { centeredIndex(listState, values.lastIndex) }
            .map { idx -> values.getOrElse(idx) { values.first() } }
            .distinctUntilChanged()
            .collectLatest { v -> onSelectedChange(v) }
    }

    Box(
        modifier = modifier.height(wheelHeight),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .background(AppColors.GenderUnselectedBackground, RoundedCornerShape(AppDimens.AgeHighlightCorner))
        )
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = inset),
            flingBehavior = snapFling,
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true,
        ) {
            items(values.size, key = { values[it] }) { idx ->
                val v = values[idx]
                val color =
                    if (idx == centerRowIndex) AppColors.GenderPrimary else AppColors.GenderUnselectedContent
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            scope.launch {
                                listState.animateScrollToItem(idx)
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = v.toString(),
                        color = color,
                        style = wheelNumberStyle,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

private fun centeredIndex(listState: LazyListState, lastIndex: Int): Int {
    val info = listState.layoutInfo
    val items = info.visibleItemsInfo
    if (items.isEmpty()) return 0
    val viewportMiddle = (info.viewportStartOffset + info.viewportEndOffset) / 2
    var best = items.first().index
    var bestDist = Int.MAX_VALUE
    for (it in items) {
        val middle = it.offset + it.size / 2
        val d = kotlin.math.abs(middle - viewportMiddle)
        if (d < bestDist) {
            bestDist = d
            best = it.index
        }
    }
    return best.coerceIn(0, lastIndex)
}
