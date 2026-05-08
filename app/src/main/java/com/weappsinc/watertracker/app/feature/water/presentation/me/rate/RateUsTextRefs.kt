package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import androidx.annotation.StringRes
import com.weappsinc.watertracker.R

/** Ánh xạ tone → resource chuỗi (không chứa Compose). */
object RateUsTextRefs {

    data class TitleBody(
        @StringRes val titleRes: Int?,
        @StringRes val bodyRes: Int,
    )

    fun forTone(tone: RateUsTone): TitleBody = when (tone) {
        RateUsTone.Intro -> TitleBody(null, R.string.rate_sheet_intro_body)
        RateUsTone.Low -> TitleBody(R.string.rate_sheet_oh_no_title, R.string.rate_sheet_feedback_body)
        RateUsTone.PositiveFour ->
            TitleBody(R.string.rate_sheet_title_four, R.string.rate_sheet_positive_body)
        RateUsTone.PositiveFive ->
            TitleBody(R.string.rate_sheet_title_five, R.string.rate_sheet_positive_body)
    }

    fun forStars(stars: Int): TitleBody = forTone(RateUsToneResolver.fromStars(stars))
}
