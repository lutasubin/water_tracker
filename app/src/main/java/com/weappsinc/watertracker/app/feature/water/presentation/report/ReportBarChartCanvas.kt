package com.weappsinc.watertracker.app.feature.water.presentation.report

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportBarUi

/**
 * Biểu đồ cột: trục Y + lưới nét đứt, cột bo tròn (pill), nhãn X hai dòng, tooltip khi chọn.
 * Tháng: [isMonthScroll] để canvas rộng theo số ngày (cha cuộn ngang).
 */
@Composable
fun ReportBarChartCanvas(
    bars: List<ReportBarUi>,
    chartMaxMl: Int,
    selectedIndex: Int?,
    onBarClick: (Int) -> Unit,
    isMonthScroll: Boolean,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val yTicks = remember(chartMaxMl) { ReportChartAxisUtils.yTicks(chartMaxMl) }
    val yTop = remember(yTicks) { yTicks.last().coerceAtLeast(1) }
    val axisStyle = remember {
        TextStyle(color = AppColors.HomeMuted, fontSize = 11.sp, fontWeight = FontWeight.Medium)
    }
    val axisSelStyle = remember {
        TextStyle(color = AppColors.HomeTitle, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
    val tooltipStyle = remember {
        TextStyle(color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
    val totalH = AppDimens.ReportChartCanvasHeight
    val bottomPad = 44.dp
    val topPad = 8.dp
    val yAxisW = AppDimens.ReportChartYAxisWidth
    val plotTopExtra = 36.dp
    val endPad = 12.dp
    val minSlot = 44.dp
    val yAxisPx = with(density) { yAxisW.toPx() }
    val endPadPx = with(density) { endPad.toPx() }
    val bottomPadPx = with(density) { bottomPad.toPx() }
    val topPadPx = with(density) { topPad.toPx() }
    val plotTopExtraPx = with(density) { plotTopExtra.toPx() }

    BoxWithConstraints(modifier = modifier) {
        val n = bars.size.coerceAtLeast(1)
        val chartW = if (isMonthScroll) {
            maxOf(maxWidth, minSlot * n + yAxisW + endPad)
        } else {
            maxWidth
        }
        Canvas(
            Modifier
                .width(chartW)
                .height(totalH)
                .pointerInput(n, yAxisPx, endPadPx, bottomPadPx, topPadPx, plotTopExtraPx) {
                    detectTapGestures { pos ->
                        val plotRight = size.width - endPadPx
                        if (pos.x < yAxisPx || pos.x > plotRight || pos.y < topPadPx || pos.y > size.height) {
                            return@detectTapGestures
                        }
                        val slot = (plotRight - yAxisPx) / n
                        val i = ((pos.x - yAxisPx) / slot).toInt().coerceIn(0, n - 1)
                        onBarClick(i)
                    }
                }
        ) {
            drawReportChartContent(
                textMeasurer = textMeasurer,
                yTicks = yTicks,
                yTop = yTop,
                bars = bars,
                selectedIndex = selectedIndex,
                axisStyle = axisStyle,
                axisSelStyle = axisSelStyle,
                tooltipStyle = tooltipStyle,
                yAxisPx = yAxisPx,
                endPadPx = endPadPx,
                bottomPadPx = bottomPadPx,
                topPadPx = topPadPx,
                plotTopExtraPx = plotTopExtraPx
            )
        }
    }
}
