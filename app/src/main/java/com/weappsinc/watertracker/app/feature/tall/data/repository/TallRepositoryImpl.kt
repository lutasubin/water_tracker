package com.weappsinc.watertracker.app.feature.tall.data.repository

import com.weappsinc.watertracker.app.core.local.GenderSQLiteHelper
import com.weappsinc.watertracker.app.feature.tall.domain.repository.TallRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TallRepositoryImpl(private val helper: GenderSQLiteHelper) : TallRepository {
    private val selectedTall = MutableStateFlow(helper.getTall() ?: 170)

    override fun observeTall(): Flow<Int> = selectedTall.asStateFlow()

    override suspend fun saveTall(tall: Int) {
        helper.saveTall(tall)
        selectedTall.value = tall
    }
}
