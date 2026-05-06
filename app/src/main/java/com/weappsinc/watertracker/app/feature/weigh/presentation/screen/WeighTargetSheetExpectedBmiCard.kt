package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.components.bmiCategoryLabel
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.weigh.domain.model.BmiCategory
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ClassifyBmiUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.util.BmiCalculator
import com.weappsinc.watertracker.app.feature.weigh.domain.util.BmiScaleGeometry
import java.util.Locale
import kotlin.math.roundToInt

/** Thẻ BMI dự kiến theo mục tiêu đang chọn (draft kg). */
@Composable
fun WeighTargetSheetExpectedBmiCard(heightCm: Int, targetDraftKg: Float) {
    val weightForBmi = targetDraftKg.roundToInt().coerceAtLeast(1)
    val bmi = remember(heightCm, targetDraftKg) {
        if (heightCm > 0) BmiCalculator.computeBmi(heightCm, weightForBmi) else 0f
    }
    val category = remember(bmi) { ClassifyBmiUseCase.classify(bmi) }
    val (low, norm) = remember { BmiScaleGeometry.segmentThresholds() }
    val fraction = remember(bmi) { BmiScaleGeometry.indicatorFraction(bmi) }
    val badgeFg = when (category) {
        BmiCategory.Underweight -> AppColors.BmiBadgeUnderText
        BmiCategory.Normal -> AppColors.BmiBadgeNormalText
        BmiCategory.Overweight -> AppColors.BmiBadgeOverText
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.WeighExpectedBmiCardBg)
            .padding(16.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.expected_bmi_label),
                modifier = Modifier.weight(1f),
                style = AppTypography.BodyMedium,
                color = AppColors.HomeMuted
            )
            Surface(color = AppColors.HomeCard, shape = RoundedCornerShape(50)) {
                Text(
                    text = bmiCategoryLabel(category),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = AppTypography.BodyMedium,
                    color = badgeFg
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            val bmiTxt = if (heightCm > 0) String.format(Locale.US, "%.1f", bmi) else "--"
            Text(text = bmiTxt, style = AppTypography.StatCardValue, color = AppColors.HomeTitle)
            Text(
                text = " ${stringResource(R.string.weigh_bmi_unit_label)}",
                style = AppTypography.BodyMedium,
                color = AppColors.HomeMuted
            )
        }
        Spacer(Modifier.height(12.dp))
        WeighBmiScaleBar(lowEndFraction = low, normalEndFraction = norm, indicatorFraction = fraction)
    }
}
