package com.weappsinc.watertracker.app.feature.age.data.repository

import com.weappsinc.watertracker.app.core.local.GenderSQLiteHelper
import com.weappsinc.watertracker.app.feature.age.domain.repository.AgeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AgeRepositoryImpl(private val helper: GenderSQLiteHelper) : AgeRepository {
    private val selectedAge = MutableStateFlow(helper.getAge() ?: 26)

    override fun observeAge(): Flow<Int> = selectedAge.asStateFlow()

    override suspend fun saveAge(age: Int) {
        helper.saveAge(age)
        selectedAge.value = age
    }
}
