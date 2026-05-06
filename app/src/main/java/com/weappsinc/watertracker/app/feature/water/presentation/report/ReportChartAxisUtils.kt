package com.weappsinc.watertracker.app.feature.water.presentation.report

import java.util.Locale
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

/** Tính nhãn trục Y và tách nhãn trục X (2 dòng) cho biểu đồ Report. */
object ReportChartAxisUtils {

    fun splitXLabel(label: String): Pair<String, String> {
        val t = label.trim()
        val space = t.indexOf(' ')
        if (space in 1 until t.length - 1) {
            return t.substring(0, space) to t.substring(space + 1)
        }
        val dash = t.indexOf('-')
        if (dash in 1 until t.lastIndex) {
            return t.substring(0, dash) to t.substring(dash + 1)
        }
        return t to ""
    }

    fun yTicks(maxMl: Int): List<Int> {
        val max = maxMl.coerceAtLeast(100)
        val roughStep = max / 5f
        val exp = floor(log10(roughStep.toDouble().coerceAtLeast(1.0))).toInt()
        val pow10 = 10.0.pow(exp).toFloat()
        val fraction = roughStep / pow10
        val niceFraction = when {
            fraction <= 1f -> 1f
            fraction <= 2f -> 2f
            fraction <= 5f -> 5f
            else -> 10f
        }
        val step = (niceFraction * pow10).toInt().coerceAtLeast(1)
        val top = ((max + step - 1) / step) * step
        val out = ArrayList<Int>()
        var v = 0
        while (v <= top) {
            out.add(v)
            v += step
        }
        if (out.isEmpty()) out.add(0)
        return out
    }

    fun formatYLabel(ml: Int): String {
        if (ml >= 1000) {
            val k = ml / 1000f
            val whole = ml % 1000 == 0
            return if (whole) "${k.toInt()}K" else String.format(Locale.US, "%.1fK", k)
        }
        return ml.toString()
    }
}
