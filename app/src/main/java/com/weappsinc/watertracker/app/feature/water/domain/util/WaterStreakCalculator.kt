package com.weappsinc.watertracker.app.feature.water.domain.util

/** Đếm streak: lùi từ today, mỗi ngày đủ goal thì +1, dừng khi thiếu goal hoặc trước ngày cài. */
object WaterStreakCalculator {
    fun compute(
        todayEpochDay: Long,
        firstInstallEpochDay: Long,
        goalMl: Int,
        intakeMlForDay: (Long) -> Int
    ): Int {
        if (goalMl <= 0) return 0
        var day = todayEpochDay
        var count = 0
        while (day >= firstInstallEpochDay) {
            if (intakeMlForDay(day) >= goalMl) count++ else break
            day--
        }
        return count
    }
}
