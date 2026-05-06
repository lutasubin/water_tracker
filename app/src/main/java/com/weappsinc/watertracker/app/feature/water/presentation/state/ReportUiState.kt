package com.weappsinc.watertracker.app.feature.water.presentation.state

enum class ReportPeriod {
    Day,
    Week,
    Month
}

/** Khóa tóm tắt báo cáo — chuỗi hiển thị resolve ở UI qua stringResource. */
enum class ReportSummaryLabel {
    Today,
    Goal,
    AvgPerDay,
    Total,
}

data class ReportBarUi(
    val label: String,
    val valueMl: Int,
    val epochDay: Long? = null
)

data class ReportRecordUi(
    val timeLabel: String,
    val amountMl: Int
)

/** Pager cửa sổ 7 ngày (tab Month): thumb bên phải = cửa sổ gần nhất. */
data class ReportMonthPagerUi(
    val windowIndex: Int,
    val maxWindowIndex: Int,
    val canGoOlder: Boolean,
    val canGoNewer: Boolean,
    val thumbStartFraction: Float,
    val thumbWidthFraction: Float
)

data class ReportUiState(
    val period: ReportPeriod,
    val anchorDateLabel: String,
    val goalMl: Int,
    val summaryLeftLabel: ReportSummaryLabel,
    val summaryLeftValueMl: Int,
    val summaryLeftHighlightPrimary: Boolean,
    val summaryRightLabel: ReportSummaryLabel,
    val summaryRightValueMl: Int,
    val chartBars: List<ReportBarUi>,
    val chartMaxMl: Int,
    val selectedBarIndex: Int?,
    val records: List<ReportRecordUi>,
    val monthPager: ReportMonthPagerUi? = null
)
