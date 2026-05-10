package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.SaveWeightUseCase
import kotlin.math.roundToInt

/** Ghi log rồi mirror cân hồ sơ — một luồng cho hướng B. */
class SaveWeightProfileAndWeighLogUseCase(
    private val saveWeighLog: SaveWeighLogUseCase,
    private val saveWeightProfile: SaveWeightUseCase
) {
    suspend operator fun invoke(weightKg: Float): Result<Unit> {
        saveWeighLog(weightKg).getOrElse { return Result.failure(it) }
        saveWeightProfile(MassDisplay.snapTargetKg(weightKg).roundToInt())
        return Result.success(Unit)
    }
}
