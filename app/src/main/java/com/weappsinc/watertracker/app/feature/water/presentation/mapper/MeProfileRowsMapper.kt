package com.weappsinc.watertracker.app.feature.water.presentation.mapper

import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay

/** Gộp số liệu hồ sơ (chiều cao/cân/tuổi/giới) → chuỗi hiển thị hàng tab Me. */
object MeProfileRowsMapper {

    fun map(
        tallCm: Int,
        weightKg: Int,
        age: Int,
        sex: GenderType,
        massUnit: MassUnit,
    ): MeProfileRowDisplay {
        val heightValueText = if (tallCm > 0) tallCm.toString() else "--"
        val weightValueText =
            if (weightKg > 0) MassDisplay.formatWeight(weightKg, massUnit) else "--"
        val ageValueText = if (age > 0) age.toString() else "--"
        return MeProfileRowDisplay(
            heightValueText = heightValueText,
            weightValueText = weightValueText,
            ageValueText = ageValueText,
            sex = sex,
            displayMassUnit = massUnit,
        )
    }
}

/** Chuỗi hiển thị phần giá trị (đơn vị cm/kg|lb gắn ở UI). */
data class MeProfileRowDisplay(
    val heightValueText: String,
    val weightValueText: String,
    val ageValueText: String,
    val sex: GenderType,
    val displayMassUnit: MassUnit,
)
