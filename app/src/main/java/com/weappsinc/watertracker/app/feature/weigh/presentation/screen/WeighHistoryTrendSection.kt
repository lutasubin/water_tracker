package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighHistoryChartPoint
import java.util.Locale

@Composable
fun WeighHistoryTrendSection(
    chartPoints: List<WeighHistoryChartPoint>,
    xLabels: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .background(AppColors.HomeCard)
            .padding(AppDimens.HomeCardInnerPadding)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = AppText.WEIGH_HISTORY_TREND_TITLE,
                style = AppTypography.BodyMedium,
                color = AppColors.WeighGoalLabelMuted
            )
            Text(
                text = AppText.WEIGH_HISTORY_LAST_7_DAYS,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(AppColors.WeighHistoryBadgeBg)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                style = AppTypography.BodyMedium,
                color = AppColors.WeighHistoryAccent
            )
        }
        Spacer(Modifier.height(12.dp))
        if (chartPoints.isEmpty()) {
            Text(
                text = AppText.WEIGH_HISTORY_EMPTY,
                style = AppTypography.BodyMedium,
                color = AppColors.HomeMuted
            )
        } else {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = String.format(Locale.US, "%.1f", chartPoints.first().weightKg),
                    style = AppTypography.BodyMedium,
                    color = AppColors.WeighHistoryAccent
                )
                Text(
                    text = String.format(Locale.US, "%.1f", chartPoints.last().weightKg),
                    style = AppTypography.BodyMedium,
                    color = AppColors.WeighHistoryAccent
                )
            }
            Spacer(Modifier.height(8.dp))
            WeighHistoryLineChart(chartPoints = chartPoints)
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth()) {
                xLabels.forEach { label ->
                    Text(
                        text = label,
                        modifier = Modifier.weight(1f),
                        style = AppTypography.BodyMedium,
                        color = AppColors.HomeMuted,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
private fun WeighHistoryLineChart(chartPoints: List<WeighHistoryChartPoint>, modifier: Modifier = Modifier) {
    val accent = AppColors.WeighHistoryAccent
    val areaTop = AppColors.WeighHistoryChartAreaAlpha
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
    ) {
        val n = chartPoints.size
        if (n < 2) {
            val cx = size.width / 2f
            val cy = size.height / 2f
            drawCircle(
                color = accent,
                radius = 6.dp.toPx(),
                center = Offset(cx, cy)
            )
            drawCircle(
                color = Color.White,
                radius = 4.dp.toPx(),
                center = Offset(cx, cy)
            )
            return@Canvas
        }
        val padL = 4.dp.toPx()
        val padR = 4.dp.toPx()
        val padT = 8.dp.toPx()
        val padB = 8.dp.toPx()
        val cw = size.width - padL - padR
        val ch = size.height - padT - padB
        val minW = chartPoints.minOf { it.weightKg } - 1f
        val maxW = chartPoints.maxOf { it.weightKg } + 1f
        val rng = (maxW - minW).coerceAtLeast(0.5f)
        fun xAt(i: Int) = padL + cw * (i / (n - 1).toFloat())
        fun yAt(w: Float) = padT + ch * (1f - (w - minW) / rng)
        val linePath = Path().apply {
            moveTo(xAt(0), yAt(chartPoints[0].weightKg))
            for (i in 1 until n) lineTo(xAt(i), yAt(chartPoints[i].weightKg))
        }
        val fillPath = Path().apply {
            moveTo(xAt(0), yAt(chartPoints[0].weightKg))
            for (i in 1 until n) lineTo(xAt(i), yAt(chartPoints[i].weightKg))
            lineTo(xAt(n - 1), padT + ch)
            lineTo(xAt(0), padT + ch)
            close()
        }
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(areaTop, Color.Transparent),
                startY = padT,
                endY = padT + ch
            )
        )
        drawPath(
            path = linePath,
            color = accent,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
        for (i in 0 until n) {
            val ox = xAt(i)
            val oy = yAt(chartPoints[i].weightKg)
            drawCircle(color = accent, radius = 6.dp.toPx(), center = Offset(ox, oy))
            drawCircle(color = Color.White, radius = 4.dp.toPx(), center = Offset(ox, oy))
        }
    }
}
