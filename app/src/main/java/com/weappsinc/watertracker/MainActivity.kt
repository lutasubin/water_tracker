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
import com.weappsinc.watertracker.app.feature.tall.data.repository.TallRepositoryImpl
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.ObserveTallUseCase
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.SaveTallUseCase
import com.weappsinc.watertracker.app.feature.tall.presentation.viewmodel.TallViewModelFactory
import com.weappsinc.watertracker.app.feature.weight.data.repository.WeightRepositoryImpl
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.SaveWeightUseCase
import com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel.WeightViewModelFactory
import com.weappsinc.watertracker.app.feature.exercise.data.repository.ExerciseRepositoryImpl
import com.weappsinc.watertracker.app.feature.exercise.domain.usecase.ObserveExerciseLevelUseCase
import com.weappsinc.watertracker.app.feature.exercise.domain.usecase.SaveExerciseLevelUseCase
import com.weappsinc.watertracker.app.feature.exercise.presentation.viewmodel.ExerciseSelectionViewModelFactory
import com.weappsinc.watertracker.app.feature.water.data.repository.WaterGoalRepositoryImpl
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveWaterGoalMlUseCase
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterGoalViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val helper = GenderSQLiteHelper(applicationContext)
        val genderRepository = GenderRepositoryImpl(helper)
        val ageRepository = AgeRepositoryImpl(helper)
        val tallRepository = TallRepositoryImpl(helper)
        val weightRepository = WeightRepositoryImpl(helper)
        val exerciseRepository = ExerciseRepositoryImpl(helper)
        val genderFactory = GenderViewModelFactory(
            observeSelectedGenderUseCase = ObserveSelectedGenderUseCase(genderRepository),
            saveSelectedGenderUseCase = SaveSelectedGenderUseCase(genderRepository)
        )
        val ageFactory = AgeViewModelFactory(
            observeAge = ObserveAgeUseCase(ageRepository),
            saveAge = SaveAgeUseCase(ageRepository)
        )
        val tallFactory = TallViewModelFactory(
            observeTall = ObserveTallUseCase(tallRepository),
            saveTall = SaveTallUseCase(tallRepository)
        )
        val weightFactory = WeightViewModelFactory(
            observeWeight = ObserveWeightUseCase(weightRepository),
            saveWeight = SaveWeightUseCase(weightRepository)
        )
        val exerciseFactory = ExerciseSelectionViewModelFactory(
            observeExerciseLevel = ObserveExerciseLevelUseCase(exerciseRepository),
            saveExerciseLevel = SaveExerciseLevelUseCase(exerciseRepository)
        )
        val waterRepository = WaterGoalRepositoryImpl(
            ageRepository = ageRepository,
            tallRepository = tallRepository,
            weightRepository = weightRepository,
            exerciseRepository = exerciseRepository
        )
        val waterGoalFactory = WaterGoalViewModelFactory(
            observeWaterGoalMl = ObserveWaterGoalMlUseCase(waterRepository)
        )
        setContent {
            AppNavHost(
                genderFactory = genderFactory,
                ageFactory = ageFactory,
                tallFactory = tallFactory,
                weightFactory = weightFactory,
                exerciseFactory = exerciseFactory,
                waterGoalFactory = waterGoalFactory
            )
        }
    }
}
