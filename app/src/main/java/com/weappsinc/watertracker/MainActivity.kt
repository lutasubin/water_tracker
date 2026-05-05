package com.weappsinc.watertracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import com.weappsinc.watertracker.app.core.local.GenderSQLiteHelper
import com.weappsinc.watertracker.app.core.navigation.AppNavHost
import com.weappsinc.watertracker.app.feature.age.data.repository.AgeRepositoryImpl
import com.weappsinc.watertracker.app.feature.age.domain.usecase.ObserveAgeUseCase
import com.weappsinc.watertracker.app.feature.age.domain.usecase.SaveAgeUseCase
import com.weappsinc.watertracker.app.feature.age.presentation.viewmodel.AgeViewModelFactory
import com.weappsinc.watertracker.app.feature.exercise.data.repository.ExerciseRepositoryImpl
import com.weappsinc.watertracker.app.feature.exercise.domain.usecase.ObserveExerciseLevelUseCase
import com.weappsinc.watertracker.app.feature.exercise.domain.usecase.SaveExerciseLevelUseCase
import com.weappsinc.watertracker.app.feature.exercise.presentation.viewmodel.ExerciseSelectionViewModelFactory
import com.weappsinc.watertracker.app.feature.gender.data.repository.GenderRepositoryImpl
import com.weappsinc.watertracker.app.feature.gender.domain.usecase.ObserveSelectedGenderUseCase
import com.weappsinc.watertracker.app.feature.gender.domain.usecase.SaveSelectedGenderUseCase
import com.weappsinc.watertracker.app.feature.gender.presentation.viewmodel.GenderViewModelFactory
import com.weappsinc.watertracker.app.feature.tall.data.repository.TallRepositoryImpl
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.ObserveTallUseCase
import com.weappsinc.watertracker.app.feature.tall.domain.usecase.SaveTallUseCase
import com.weappsinc.watertracker.app.feature.tall.presentation.viewmodel.TallViewModelFactory
import com.weappsinc.watertracker.app.feature.water.data.local.WaterTrackingDatabase
import com.weappsinc.watertracker.app.feature.water.data.preferences.WaterPreferencesRepositoryImpl
import com.weappsinc.watertracker.app.feature.water.data.repository.WaterGoalRepositoryImpl
import com.weappsinc.watertracker.app.feature.water.data.repository.WaterIntakeRepositoryImpl
import com.weappsinc.watertracker.app.feature.water.domain.usecase.AddWaterIntakeUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.EnsureFirstInstallDayUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveSavedGoalMlUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveSavedUnitUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveWaterGoalMlUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.SaveOnboardingWaterGoalUseCase
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterGoalViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModelFactory
import com.weappsinc.watertracker.app.feature.weight.data.repository.WeightRepositoryImpl
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.SaveWeightUseCase
import com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel.WeightViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
        val observeWaterGoalMl = ObserveWaterGoalMlUseCase(waterRepository)

        val waterPrefs = WaterPreferencesRepositoryImpl(applicationContext)
        val saveOnboardingWaterGoal = SaveOnboardingWaterGoalUseCase(waterPrefs)
        val observeSavedGoalMl = ObserveSavedGoalMlUseCase(waterPrefs)
        val observeSavedUnit = ObserveSavedUnitUseCase(waterPrefs)
        val ensureFirstInstallDayUseCase = EnsureFirstInstallDayUseCase(waterPrefs)

        val waterGoalFactoryOnboarding = WaterGoalViewModelFactory(
            observeWaterGoalMl = observeWaterGoalMl,
            saveOnboardingWaterGoal = saveOnboardingWaterGoal,
            editMode = false,
            observeSavedGoalMl = observeSavedGoalMl,
            observeSavedUnit = observeSavedUnit
        )
        val waterGoalFactoryEdit = WaterGoalViewModelFactory(
            observeWaterGoalMl = observeWaterGoalMl,
            saveOnboardingWaterGoal = saveOnboardingWaterGoal,
            editMode = true,
            observeSavedGoalMl = observeSavedGoalMl,
            observeSavedUnit = observeSavedUnit
        )

        val waterDb = WaterTrackingDatabase.create(applicationContext)
        val intakeRepository = WaterIntakeRepositoryImpl(waterDb.waterIntakeDao())
        val addWaterIntakeUseCase = AddWaterIntakeUseCase(intakeRepository)
        val waterTrackerFactory = WaterTrackerViewModelFactory(
            prefs = waterPrefs,
            intake = intakeRepository,
            addWaterIntake = addWaterIntakeUseCase
        )

        setContent {
            // Vùng an toàn: tránh đè status bar, tai thỏ, thanh điều hướng (gesture/3 nút).
            Box(
                Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                AppNavHost(
                    genderFactory = genderFactory,
                    ageFactory = ageFactory,
                    tallFactory = tallFactory,
                    weightFactory = weightFactory,
                    exerciseFactory = exerciseFactory,
                    waterGoalFactoryOnboarding = waterGoalFactoryOnboarding,
                    waterGoalFactoryEdit = waterGoalFactoryEdit,
                    waterTrackerFactory = waterTrackerFactory,
                    ensureFirstInstallDayUseCase = ensureFirstInstallDayUseCase,
                    observeSavedGoalMlUseCase = observeSavedGoalMl
                )
            }
        }
    }
}
