package com.weappsinc.watertracker.app.feature.water.domain.util

/** Chuỗi ngày mở app liên tiếp lùi từ một mốc; trước ngày cài không đếm. */
object WaterStreakCalculator {

    /**
     * Sang ngày mới nhưng chưa vào đủ lần: vẫn giữ streak tới **hôm qua**
     * (chỉ về trước hôm nay khi hôm nay đã được ghi mở app).
     */
    fun computeForDisplay(
        todayEpochDay: Long,
        firstInstallEpochDay: Long,
        openedEpochDays: Set<Long>
    ): Int {
        val incl = streakBackFrom(todayEpochDay, firstInstallEpochDay, openedEpochDays)
        if (incl > 0) return incl
        val yesterday = todayEpochDay - 1
        if (yesterday < firstInstallEpochDay) return 0
        return streakBackFrom(yesterday, firstInstallEpochDay, openedEpochDays)
    }

    private fun streakBackFrom(
        startEpochDay: Long,
        firstInstallEpochDay: Long,
        openedEpochDays: Set<Long>
    ): Int {
        var day = startEpochDay
        var count = 0
        while (day >= firstInstallEpochDay) {
            if (openedEpochDays.contains(day)) count++ else break
            day--
        }
        return count
    }
}
