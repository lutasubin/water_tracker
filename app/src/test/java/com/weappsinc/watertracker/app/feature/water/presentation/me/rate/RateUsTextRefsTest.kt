package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import com.weappsinc.watertracker.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RateUsTextRefsTest {

    @Test
    fun toneIntro_titleNull() {
        val tb = RateUsTextRefs.forTone(RateUsTone.Intro)
        assertNull(tb.titleRes)
        assertEquals(R.string.rate_sheet_intro_body, tb.bodyRes)
    }

    @Test
    fun toneLow_coOhNo() {
        val tb = RateUsTextRefs.forTone(RateUsTone.Low)
        assertEquals(R.string.rate_sheet_oh_no_title, tb.titleRes)
        assertEquals(R.string.rate_sheet_feedback_body, tb.bodyRes)
    }

    @Test
    fun forStars_mapBucket() {
        assertEquals(RateUsTone.Intro, RateUsToneResolver.fromStars(0))
        assertEquals(RateUsTone.Low, RateUsToneResolver.fromStars(2))
        assertEquals(RateUsTone.PositiveFour, RateUsToneResolver.fromStars(4))
        assertEquals(RateUsTone.PositiveFive, RateUsToneResolver.fromStars(5))
        assertEquals(R.string.rate_sheet_title_five, RateUsTextRefs.forStars(5).titleRes)
    }
}
