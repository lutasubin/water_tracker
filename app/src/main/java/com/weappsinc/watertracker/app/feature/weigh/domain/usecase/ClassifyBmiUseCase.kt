package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.BmiCategory

/** Ngưỡng hiển thị: <18.5 gầy, 18.5–24.9 bình thường, ≥25 thừa cân. */
class ClassifyBmiUseCase {
    operator fun invoke(bmi: Float): BmiCategory = Companion.classify(bmi)

    companion object {
        fun classify(bmi: Float): BmiCategory = when {
            bmi <= 0f -> BmiCategory.Normal
            bmi < 18.5f -> BmiCategory.Underweight
            bmi < 25f -> BmiCategory.Normal
            else -> BmiCategory.Overweight
        }
    }
}
