package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import kotlin.math.abs

/**
 * Tiến triển kg: so khớp draft với mốc tham chiếu hợp lý (lần ghi trước / mốc hành trình).
 * Xem plan: nếu draft trùng bản ghi mới nhất → so với bản ghi liền trước hoặc mốc bắt đầu.
 */
class ComputeWeightProgressDeltaUseCase {

    companion object {
        private const val SAME_AS_LAST_EPS = 0.05f
    }

    /** [fallbackStartKg] = mốc hành trình hoặc cân hồ sơ khi chưa có mốc. */
    operator fun invoke(
        draftKg: Float,
        latestLog: WeighLogEntry?,
        beforeLatestLog: WeighLogEntry?,
        fallbackStartKg: Float?
    ): Float? {
        val start = fallbackStartKg ?: return null
        if (latestLog == null) return draftKg - start
        val lastW = latestLog.weightKg.toFloat()
        val penW = beforeLatestLog?.weightKg?.toFloat()
        val wRef = if (abs(draftKg - lastW) < SAME_AS_LAST_EPS) {
            penW ?: start
        } else {
            lastW
        }
        return draftKg - wRef
    }

    /** Với mục tiêu giảm cân (target < start), delta < 0 là có lợi. */
    fun isDeltaFavorable(delta: Float, isLossGoal: Boolean): Boolean {
        if (delta == 0f) return true
        return if (isLossGoal) delta < 0 else delta > 0
    }
}
