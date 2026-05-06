package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighHistoryChartPoint

/** Biểu đồ đường 7 ngày: curve, gradient, chấm tròn viền tím, nhãn đầu–cuối. */
@Composable
fun WeighHistoryLineChart(
    chartPoints: List<WeighHistoryChartPoint>,
    modifier: Modifier = Modifier,
) {
    val tm = rememberTextMeasurer()
    val labelStyle = AppTypography.BodyMedium.copy(color = AppColors.HomeMuted)
    Canvas(
        modifier
            .fillMaxWidth()
            .height(WeighDimens.WeighTrendChartCanvasHeight)
    ) {
        drawWeighHistoryLineChartContent(chartPoints, tm, labelStyle)
    }
}
