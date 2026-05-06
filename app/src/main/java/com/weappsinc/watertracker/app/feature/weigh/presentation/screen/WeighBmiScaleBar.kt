package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.WeighDimens

/** Thanh BMI 3 màu + vòng tròn căn giữa thanh, layer sau đè lên line. */
@Composable
fun WeighBmiScaleBar(
    lowEndFraction: Float,
    normalEndFraction: Float,
    indicatorFraction: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(WeighDimens.BmiScaleCanvasHeight)
    ) {
        Canvas(
            Modifier
                .fillMaxWidth()
                .height(WeighDimens.BmiBarHeight)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(WeighDimens.BmiBarHeight / 2))
        ) {
            val w = size.width
            val h = size.height
            drawRect(
                AppColors.BmiScaleLow,
                size = Size(w * lowEndFraction, h)
            )
            drawRect(
                AppColors.BmiScaleNormal,
                topLeft = Offset(w * lowEndFraction, 0f),
                size = Size(w * (normalEndFraction - lowEndFraction), h)
            )
            drawRect(
                AppColors.BmiScaleHigh,
                topLeft = Offset(w * normalEndFraction, 0f),
                size = Size(w * (1f - normalEndFraction), h)
            )
        }
        Canvas(Modifier.fillMaxSize()) {
            val rDot = WeighDimens.BmiIndicatorOuter.toPx() / 2f
            val cx = (size.width * indicatorFraction).coerceIn(rDot, size.width - rDot)
            val cy = size.height / 2f
            val strokeW = WeighDimens.BmiIndicatorStrokeWidth.toPx()
            drawCircle(AppColors.BmiIndicatorFill, rDot, Offset(cx, cy))
            drawCircle(
                color = AppColors.BmiIndicatorStroke,
                radius = rDot,
                center = Offset(cx, cy),
                style = Stroke(width = strokeW)
            )
        }
    }
}
