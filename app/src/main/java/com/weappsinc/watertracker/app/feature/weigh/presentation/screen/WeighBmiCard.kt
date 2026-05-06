package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.domain.model.BmiCategory

@Composable
fun WeighBmiCard(
    bmiValueText: String,
    category: BmiCategory,
    lowEndFraction: Float,
    normalEndFraction: Float,
    indicatorFraction: Float,
    modifier: Modifier = Modifier
) {
    val (badgeBg, badgeFg) = when (category) {
        BmiCategory.Underweight -> AppColors.BmiBadgeUnderBg to AppColors.BmiBadgeUnderText
        BmiCategory.Normal -> AppColors.BmiBadgeNormalBg to AppColors.BmiBadgeNormalText
        BmiCategory.Overweight -> AppColors.BmiBadgeOverBg to AppColors.BmiBadgeOverText
    }
    val badgeLabel = when (category) {
        BmiCategory.Underweight -> AppText.BMI_UNDERWEIGHT
        BmiCategory.Normal -> AppText.BMI_NORMAL
        BmiCategory.Overweight -> AppText.BMI_OVERWEIGHT
    }
    val badgeShape = RoundedCornerShape(50)
    Column(
        modifier = modifier
            .defaultMinSize(minHeight = AppDimens.HomeStatCardMinHeight)
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .background(AppColors.HomeCard)
            .padding(AppDimens.HomeCardInnerPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = AppText.WEIGH_BMI_SECTION_TITLE,
                modifier = Modifier.weight(1f),
                color = AppColors.HomeMuted,
                style = AppTypography.BodyMedium
            )
            Surface(color = badgeBg, shape = badgeShape) {
                Text(
                    text = badgeLabel,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    color = badgeFg,
                    style = AppTypography.BodyMedium
                )
            }
        }
        Spacer(Modifier.height(WeighDimens.BmiSectionTitleSpacing))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = bmiValueText,
                color = AppColors.HomeTitle,
                style = AppTypography.StatCardValue
            )
            Text(
                text = " ${AppText.WEIGH_BMI_UNIT_LABEL}",
                color = AppColors.HomeMuted,
                style = AppTypography.BodyMedium
            )
        }
        Spacer(Modifier.height(WeighDimens.BmiValueBadgeSpacing))
        WeighBmiScaleBar(
            lowEndFraction = lowEndFraction,
            normalEndFraction = normalEndFraction,
            indicatorFraction = indicatorFraction,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
