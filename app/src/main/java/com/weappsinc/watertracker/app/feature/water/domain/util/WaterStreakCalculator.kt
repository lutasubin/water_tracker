package com.weappsinc.watertracker.app.feature.water.domain.util

/** Đếm streak đủ goal lùi từ một ngày bắt đầu tới khi đứt hoặc trước ngày cài. */
object WaterStreakCalculator {

    /**
     * Streak chỉ các ngày đã đủ goal — dùng nội bộ / khi chỉ đến từ "hôm nay".
     */
    fun compute(
        todayEpochDay: Long,
        firstInstallEpochDay: Long,
        goalMl: Int,
        intakeMlForDay: (Long) -> Int
    ): Int =
        streakBackFrom(todayEpochDay, firstInstallEpochDay, goalMl, intakeMlForDay)

    /**
     * Streak hiển thị UI: sang ngày mới chưa uống đủ vẫn **giữ** chuỗi đến hôm qua (không về 0),
     * chỉ khi đủ goal hôm nay thì streak tăng thêm một ngày.
     */
    fun computeForDisplay(
        todayEpochDay: Long,
        firstInstallEpochDay: Long,
        goalMl: Int,
        intakeMlForDay: (Long) -> Int
    ): Int {
        if (goalMl <= 0) return 0
        val includingToday =
            streakBackFrom(todayEpochDay, firstInstallEpochDay, goalMl, intakeMlForDay)
        if (includingToday > 0) return includingToday
        val yesterday = todayEpochDay - 1
        if (yesterday < firstInstallEpochDay) return 0
        return streakBackFrom(yesterday, firstInstallEpochDay, goalMl, intakeMlForDay)
    }

    private fun streakBackFrom(
        startEpochDay: Long,
        firstInstallEpochDay: Long,
        goalMl: Int,
        intakeMlForDay: (Long) -> Int
    ): Int {
        if (goalMl <= 0) return 0
        var day = startEpochDay
        var count = 0
        while (day >= firstInstallEpochDay) {
            if (intakeMlForDay(day) >= goalMl) count++ else break
            day--
        }
        return count
    }
}
