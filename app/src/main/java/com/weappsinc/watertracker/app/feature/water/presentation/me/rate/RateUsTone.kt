package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

/** Tone copy/UI sheet Rate Us theo bucket sao (logic thuần, có unit test). */
enum class RateUsTone {
    Intro,
    Low,
    PositiveFour,
    PositiveFive,
}

object RateUsToneResolver {
    fun fromStars(stars: Int): RateUsTone = when {
        stars <= 0 -> RateUsTone.Intro
        stars <= 3 -> RateUsTone.Low
        stars == 4 -> RateUsTone.PositiveFour
        else -> RateUsTone.PositiveFive
    }
}
