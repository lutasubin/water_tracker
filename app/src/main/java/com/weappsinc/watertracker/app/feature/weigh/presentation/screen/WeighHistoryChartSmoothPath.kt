package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

private const val SMOOTH_TENSION = 0.22f

/** Đường cong cubic nối các điểm (xu hướng cân), không lưới trục Y. */
internal fun buildWeighHistorySmoothLinePath(points: List<Offset>): Path {
    val path = Path()
    if (points.isEmpty()) return path
    if (points.size == 1) {
        path.moveTo(points[0].x, points[0].y)
        return path
    }
    path.moveTo(points[0].x, points[0].y)
    for (i in 0 until points.size - 1) {
        val p0 = points.getOrElse(i - 1) { points[i] }
        val p1 = points[i]
        val p2 = points[i + 1]
        val p3 = points.getOrElse(i + 2) { p2 }
        val cp1x = p1.x + (p2.x - p0.x) * SMOOTH_TENSION
        val cp1y = p1.y + (p2.y - p0.y) * SMOOTH_TENSION
        val cp2x = p2.x - (p3.x - p1.x) * SMOOTH_TENSION
        val cp2y = p2.y - (p3.y - p1.y) * SMOOTH_TENSION
        path.cubicTo(cp1x, cp1y, cp2x, cp2y, p2.x, p2.y)
    }
    return path
}

/** Đóng vùng tô phía dưới đường cong tới mép đáy plot. */
internal fun buildWeighHistorySmoothFillPath(points: List<Offset>, bottomY: Float): Path {
    val path = buildWeighHistorySmoothLinePath(points)
    if (points.size < 2) return path
    path.lineTo(points.last().x, bottomY)
    path.lineTo(points.first().x, bottomY)
    path.close()
    return path
}
