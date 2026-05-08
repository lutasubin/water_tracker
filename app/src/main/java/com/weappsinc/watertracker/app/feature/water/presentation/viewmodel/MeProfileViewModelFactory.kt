package com.weappsinc.watertracker.app.feature.water.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterAppVisitRepository
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterIntakeRepository
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository

class MeProfileViewModelFactory(
    private val prefs: WaterPreferencesRepository,
    private val intake: WaterIntakeRepository,
    private val visits: WaterAppVisitRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MeProfileViewModel(prefs, intake, visits) as T
}
