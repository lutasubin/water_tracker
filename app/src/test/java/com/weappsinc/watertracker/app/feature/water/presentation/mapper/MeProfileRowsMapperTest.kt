package com.weappsinc.watertracker.app.feature.water.presentation.mapper

import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import org.junit.Assert.assertEquals
import org.junit.Test

class MeProfileRowsMapperTest {

    @Test
    fun mapsHeightWeightAgeAndKeepsSexAndUnit() {
        val r = MeProfileRowsMapper.map(175, 70, 30, GenderType.FEMALE, MassUnit.KG)
        assertEquals("175", r.heightValueText)
        assertEquals("70.0", r.weightValueText)
        assertEquals("30", r.ageValueText)
        assertEquals(GenderType.FEMALE, r.sex)
        assertEquals(MassUnit.KG, r.displayMassUnit)
    }

    @Test
    fun emptyDimsShowDash() {
        val r = MeProfileRowsMapper.map(0, 0, 0, GenderType.MALE, MassUnit.LB)
        assertEquals("--", r.heightValueText)
        assertEquals("--", r.weightValueText)
        assertEquals("--", r.ageValueText)
    }
}
