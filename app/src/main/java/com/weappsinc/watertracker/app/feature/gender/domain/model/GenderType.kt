package com.weappsinc.watertracker.app.feature.gender.domain.model

enum class GenderType {
    MALE,
    FEMALE,
    OTHER;

    companion object {
        fun fromValue(value: String?): GenderType {
            return entries.firstOrNull { it.name == value } ?: MALE
        }
    }
}
