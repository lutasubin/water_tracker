package com.weappsinc.watertracker.app.feature.water.presentation.report

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

/** Vẽ lưới ngang nét đứt và cột dạng viên thuốc (bo tròn đầy đủ). */
internal fun DrawScope.drawReportDashedHLine(y: Float, x0: Float, x1: Float, color: Color) {
    drawLine(
        color = color,
        start = Offset(x0, y),
        end = Offset(x1, y),
        strokeWidth = 1.dp.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f)
    )
}

internal fun DrawScope.drawPillBar(left: Float, top: Float, width: Float, bottom: Float, color: Color) {
    val w = width.coerceAtLeast(2f)
    val h = bottom - top
    if (h < 1f) return
    val r = w / 2f
    drawRoundRect(
        color = color,
        topLeft = Offset(left, top),
        size = Size(w, h),
        cornerRadius = CornerRadius(r, r)
    )
}
