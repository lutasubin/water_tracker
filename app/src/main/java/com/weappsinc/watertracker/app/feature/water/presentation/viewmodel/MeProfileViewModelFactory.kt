package com.weappsinc.watertracker.app.feature.water.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weappsinc.watertracker.app.feature.age.domain.usecase.ObserveAgeUseCase
import com.weappsinc.watertracker.app.feature.gender.domain.usecase.ObserveSelectedGenderUseCase
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.ObserveTallUseCase
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterAppVisitRepository
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterIntakeRepository
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase

class MeProfileViewModelFactory(
    private val prefs: WaterPreferencesRepository,
    private val intake: WaterIntakeRepository,
    private val visits: WaterAppVisitRepository,
    private val observeTall: ObserveTallUseCase,
    private val observeWeight: ObserveWeightUseCase,
    private val observeAge: ObserveAgeUseCase,
    private val observeSelectedGender: ObserveSelectedGenderUseCase,
    private val observeMassUnit: ObserveWeighMassUnitUseCase,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MeProfileViewModel(
            prefs,
            intake,
            visits,
            observeTall,
            observeWeight,
            observeAge,
            observeSelectedGender,
            observeMassUnit,
        ) as T
}
