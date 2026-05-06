package com.weappsinc.watertracker.app.feature.weigh.domain.util

import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import java.util.Locale
import kotlin.math.roundToInt

private const val LB_PER_KG = 2.2046226218f

/** Format hiển thị cân nặng theo đơn vị (lưu nội bộ kg Int). */
object MassDisplay {

    fun formatWeight(weightKg: Int, unit: MassUnit): String = when (unit) {
        MassUnit.KG -> String.format(Locale.US, "%.1f", weightKg.toFloat())
        MassUnit.LB -> String.format(Locale.US, "%.1f", weightKg * LB_PER_KG)
    }

    fun formatTargetKg(targetKg: Float, unit: MassUnit): String = when (unit) {
        MassUnit.KG -> String.format(Locale.US, "%.1f", targetKg)
        MassUnit.LB -> String.format(Locale.US, "%.1f", targetKg * LB_PER_KG)
    }

    /** Chuyển input LB từ slider về kg nội bộ. */
    fun lbToKg(lb: Float): Float = lb / LB_PER_KG

    /** Cân Snap target slider theo 0.5 (kg) hoặc tương đương lb. */
    fun snapTargetKg(raw: Float): Float = (raw * 2f).roundToInt() / 2f

    /** Giá trị tuyệt đối chênh lệch cân (kg) → hiển thị theo đơn vị. */
    fun formatAbsKgDelta(absKg: Float, unit: MassUnit): String {
        val v = if (unit == MassUnit.KG) absKg else absKg * LB_PER_KG
        return String.format(Locale.US, "%.1f", v)
    }

    /** Chênh lệch có dấu (kg hoặc lb tương đương). */
    fun formatSignedKgDelta(signedKg: Float, unit: MassUnit): String {
        val v = if (unit == MassUnit.KG) signedKg else signedKg * LB_PER_KG
        return String.format(Locale.US, "%+.1f", v)
    }
}
