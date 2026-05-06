package com.weappsinc.watertracker.app.feature.water.presentation.report

import java.text.NumberFormat
import java.util.Locale

object ReportAmountFormat {
    private val fmt = NumberFormat.getNumberInstance(Locale.US)

    fun formatMl(ml: Int): String = "${fmt.format(ml)}"
}
