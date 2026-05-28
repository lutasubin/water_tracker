package com.weappsinc.watertracker.app.core.ads

/** Backoff retry khi load banner/native fail. */
object AdsRetryPolicy {
    private val delaysMs = longArrayOf(800L, 1_500L, 3_000L)

    fun delayForAttempt(attempt: Int): Long {
        if (attempt < 0) return delaysMs.first()
        return delaysMs[attempt.coerceAtMost(delaysMs.lastIndex)]
    }
}
