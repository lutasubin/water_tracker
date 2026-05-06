package com.weappsinc.watertracker.app.core.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.feature.weigh.domain.model.BmiCategory

@Composable
fun bmiCategoryLabel(category: BmiCategory): String = stringResource(
    when (category) {
        BmiCategory.Underweight -> R.string.bmi_underweight
        BmiCategory.Normal -> R.string.bmi_normal
        BmiCategory.Overweight -> R.string.bmi_overweight
    }
)
