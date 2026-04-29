package com.weappsinc.watertracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.weappsinc.watertracker.app.core.navigation.AppNavHost
import com.weappsinc.watertracker.app.feature.age.data.repository.AgeRepositoryImpl
import com.weappsinc.watertracker.app.feature.age.domain.usecase.ObserveAgeUseCase
import com.weappsinc.watertracker.app.feature.age.domain.usecase.SaveAgeUseCase
import com.weappsinc.watertracker.app.feature.age.presentation.viewmodel.AgeViewModelFactory
import com.weappsinc.watertracker.app.core.local.GenderSQLiteHelper
import com.weappsinc.watertracker.app.feature.gender.data.repository.GenderRepositoryImpl
import com.weappsinc.watertracker.app.feature.gender.domain.usecase.ObserveSelectedGenderUseCase
import com.weappsinc.watertracker.app.feature.gender.domain.usecase.SaveSelectedGenderUseCase
import com.weappsinc.watertracker.app.feature.gender.presentation.viewmodel.GenderViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val helper = GenderSQLiteHelper(applicationContext)
        val genderRepository = GenderRepositoryImpl(helper)
        val ageRepository = AgeRepositoryImpl(helper)
        val genderFactory = GenderViewModelFactory(
            observeSelectedGenderUseCase = ObserveSelectedGenderUseCase(genderRepository),
            saveSelectedGenderUseCase = SaveSelectedGenderUseCase(genderRepository)
        )
        val ageFactory = AgeViewModelFactory(
            observeAge = ObserveAgeUseCase(ageRepository),
            saveAge = SaveAgeUseCase(ageRepository)
        )
        setContent {
            AppNavHost(genderFactory = genderFactory, ageFactory = ageFactory)
        }
    }
}
