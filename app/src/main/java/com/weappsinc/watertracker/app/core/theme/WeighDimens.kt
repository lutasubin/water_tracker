package com.weappsinc.watertracker.app.core.theme

import androidx.compose.ui.unit.dp

/** Kích thước/spacing riêng màn Weigh Tracker (<100 dòng). */
object WeighDimens {
    val ScreenHorizontalPadding get() = AppDimens.HomeHorizontalPadding
    val HeaderBottomSpacing = 20.dp
    val CardsSpacing = 12.dp
    val BmiCardTopSpacing = 20.dp
    val BmiBarHeight = 10.dp
    /** Đường kính vòng chỉ báo (lớn hơn chiều cao thanh để nhô ra hai phía). */
    val BmiIndicatorOuter = 20.dp
    /** Chiều cao vùng vẽ = đủ chứa vòng tròn, thanh nằm giữa. */
    val BmiScaleCanvasHeight get() = BmiIndicatorOuter
    val BmiIndicatorStrokeWidth = 2.dp
    val BmiSectionTitleSpacing = 8.dp
    val BmiValueBadgeSpacing = 12.dp
    val TargetSectionTopSpacing = 24.dp
    val TargetEmptyIconSize = 72.dp
    val TargetCtaTopSpacing = 16.dp
    val CardIconSize get() = AppDimens.HomeStatCardIconSize
    val SheetSliderSpacing = 20.dp
    val SheetHeaderIconSize = 28.dp
    val SheetStepperButtonSize = 44.dp
    val SheetInnerSpacing = 16.dp
    val SheetCtaHeight = 56.dp
    val GoalProgressBarHeight = 10.dp
    val WeighTodayStepperSize = 48.dp
    val WeighTodayStepperCorner = 14.dp
    val WeighTodayStepperIconSize = 22.dp
    val WeighTodayStatusRingSize = 18.dp
    val WeighTodayStatusDotSize = 7.dp

    /** Thẻ mục tiêu tím — màn chi tiết cân. */
    val WeighDetailHeroCorner = 28.dp
    val WeighDetailHeroPadding = 20.dp
    val WeighDetailHeroEditSize = 36.dp
    val WeighDetailHeroEditCorner = 10.dp
    val WeighDetailHeroEditIconSize = 20.dp
    val WeighDetailHeroProgressHeight = 12.dp

    /** Thẻ biểu đồ xu hướng cân — bo góc ~24dp theo mock. */
    val WeighTrendCardCorner = 24.dp
    val WeighTrendChartCanvasHeight = 200.dp
    val WeighTrendChartPadH = 4.dp
    val WeighTrendChartPadBottom = 6.dp
    val WeighTrendChartPlotTop = 10.dp
    val WeighTrendLineStroke = 2.5.dp
    val WeighTrendPointOuter = 5.5.dp
    val WeighTrendPointInner = 3.5.dp
    val WeighTrendValueLabelGap = 6.dp
    val WeighTrendBadgeHPad = 12.dp
    val WeighTrendBadgeVPad = 6.dp
}
