package com.weappsinc.watertracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import com.weappsinc.watertracker.app.core.theme.AppColors
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
import com.weappsinc.watertracker.app.feature.water.data.repository.WaterAppVisitRepositoryImpl
import com.weappsinc.watertracker.app.feature.water.domain.usecase.AddWaterIntakeUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.BuildDayChartBucketsFromLogsUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.EnsureFirstInstallDayUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveFirstInstallEpochDayUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveSavedGoalMlUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.SaveOnboardingWaterGoalUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveSavedUnitUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveWaterGoalMlUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.RecordWaterAppOpenDayUseCase
import com.weappsinc.watertracker.app.feature.water.presentation.me.rate.RateUsViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.MeProfileViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.ReportViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterGoalViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModelFactory
import com.weappsinc.watertracker.app.feature.weight.data.repository.WeightRepositoryImpl
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.ObserveWeightUseCase
import com.weappsinc.watertracker.app.feature.weight.domain.usecase.SaveWeightUseCase
import com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel.WeightViewModelFactory
import com.weappsinc.watertracker.app.feature.settings.data.repository.LocalePreferencesRepositoryImpl
import com.weappsinc.watertracker.app.feature.settings.domain.usecase.MarkLocaleOnboardingCompletedUseCase
import com.weappsinc.watertracker.app.feature.settings.domain.usecase.ObserveLocaleOnboardingCompletedUseCase
import com.weappsinc.watertracker.app.feature.weigh.data.preferences.WeighPreferencesRepositoryImpl
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ClassifyBmiUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.MapBmiToScaleFractionUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighJourneyStartWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighTargetWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighJourneyStartWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighMassUnitUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighTargetWeightKgUseCase
import com.weappsinc.watertracker.app.feature.weigh.data.local.WeighWeightLogDatabase
import com.weappsinc.watertracker.app.feature.weigh.data.repository.WeighCompletedGoalRepositoryImpl
import com.weappsinc.watertracker.app.feature.weigh.data.repository.WeighLogRepositoryImpl
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ArchiveCompletedWeightGoalUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ComputeWeightProgressDeltaUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.BuildWeighHistorySevenDayChartUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLatestLogUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLatestLogForTodayUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveCompletedWeightGoalsUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveCompletedGoalDetailUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLatestTwoLogsUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.ObserveWeighLogsLast7DaysUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeighLogUseCase
import com.weappsinc.watertracker.app.feature.weigh.domain.usecase.SaveWeightProfileAndWeighLogUseCase
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalDetailViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalHistoryDetailViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalHistoryViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighHistoryViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighTrackerViewModelFactory
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() {

    private lateinit var ensureFirstInstallDayUseCase: EnsureFirstInstallDayUseCase
    private lateinit var recordWaterAppOpenDayUseCase: RecordWaterAppOpenDayUseCase

    override fun onResume() {
        super.onResume()
        if (::ensureFirstInstallDayUseCase.isInitialized) {
            lifecycleScope.launch {
                ensureFirstInstallDayUseCase()
                recordWaterAppOpenDayUseCase()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val reportDayBucketLabels =
            resources.getStringArray(R.array.report_day_bucket_labels).toList()
        val helper = GenderSQLiteHelper(applicationContext)
        val genderRepository = GenderRepositoryImpl(helper)
        val ageRepository = AgeRepositoryImpl(helper)
        val tallRepository = TallRepositoryImpl(helper)
        val weightRepository = WeightRepositoryImpl(helper)
        val exerciseRepository = ExerciseRepositoryImpl(helper)
        val weighLogDb = WeighWeightLogDatabase.create(applicationContext)
        val weighLogRepository = WeighLogRepositoryImpl(weighLogDb.weighWeightLogDao())
        val saveWeighLog = SaveWeighLogUseCase(weighLogRepository)
        val saveWeightProfile = SaveWeightUseCase(weightRepository)
        val saveWeightProfileAndWeighLog =
            SaveWeightProfileAndWeighLogUseCase(saveWeighLog, saveWeightProfile)
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
            saveWeightProfileAndWeighLog = saveWeightProfileAndWeighLog
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
        ensureFirstInstallDayUseCase = EnsureFirstInstallDayUseCase(waterPrefs)
        val observeFirstInstallEpochDay = ObserveFirstInstallEpochDayUseCase(waterPrefs)
        val localePrefsRepo = LocalePreferencesRepositoryImpl(applicationContext)
        val observeLocaleOnboardingCompletedUseCase =
            ObserveLocaleOnboardingCompletedUseCase(localePrefsRepo)
        val markLocaleOnboardingCompletedUseCase =
            MarkLocaleOnboardingCompletedUseCase(localePrefsRepo)

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
        val visitRepository = WaterAppVisitRepositoryImpl(waterDb.waterAppVisitDao())
        recordWaterAppOpenDayUseCase = RecordWaterAppOpenDayUseCase(visitRepository)
        val addWaterIntakeUseCase = AddWaterIntakeUseCase(intakeRepository)
        val waterTrackerFactory = WaterTrackerViewModelFactory(
            prefs = waterPrefs,
            intake = intakeRepository,
            visits = visitRepository,
            addWaterIntake = addWaterIntakeUseCase,
        )
        val weighPrefs = WeighPreferencesRepositoryImpl(applicationContext)
        val meProfileFactory = MeProfileViewModelFactory(
            prefs = waterPrefs,
            intake = intakeRepository,
            visits = visitRepository,
            observeTall = ObserveTallUseCase(tallRepository),
            observeWeight = ObserveWeightUseCase(weightRepository),
            observeAge = ObserveAgeUseCase(ageRepository),
            observeSelectedGender = ObserveSelectedGenderUseCase(genderRepository),
            observeMassUnit = ObserveWeighMassUnitUseCase(weighPrefs),
        )
        val rateUsFactory = RateUsViewModelFactory()
        val buildDayBuckets = BuildDayChartBucketsFromLogsUseCase()
        val reportViewModelFactory = ReportViewModelFactory(
            prefs = waterPrefs,
            intake = intakeRepository,
            buildDayBuckets = buildDayBuckets,
            reportDayBucketLabels = reportDayBucketLabels,
        )

        val observeWeighLatestLog = ObserveWeighLatestLogUseCase(weighLogRepository)
        val weighCompletedGoalRepository =
            WeighCompletedGoalRepositoryImpl(weighLogDb.weighCompletedGoalDao())
        val observeCompletedWeightGoals =
            ObserveCompletedWeightGoalsUseCase(weighCompletedGoalRepository)
        val observeCompletedGoalDetail =
            ObserveCompletedGoalDetailUseCase(weighCompletedGoalRepository)
        val archiveCompletedWeightGoal = ArchiveCompletedWeightGoalUseCase(
            completedGoalRepository = weighCompletedGoalRepository,
            weighLogRepository = weighLogRepository,
            weighPreferencesRepository = weighPrefs,
            saveTargetWeightKg = SaveWeighTargetWeightKgUseCase(weighPrefs),
            saveJourneyStartWeightKg = SaveWeighJourneyStartWeightKgUseCase(weighPrefs),
        )
        val weighGoalHistoryFactory = WeighGoalHistoryViewModelFactory(
            observeCompleted = observeCompletedWeightGoals,
            observeMassUnit = ObserveWeighMassUnitUseCase(weighPrefs),
        )
        val weighGoalHistoryDetailFactory = { goalId: Long ->
            WeighGoalHistoryDetailViewModelFactory(
                observeCompletedDetail = observeCompletedGoalDetail,
                observeMassUnit = ObserveWeighMassUnitUseCase(weighPrefs),
                goalId = goalId,
            )
        }
        val weighTrackerFactory = WeighTrackerViewModelFactory(
            weighPrefs = weighPrefs,
            observeTall = ObserveTallUseCase(tallRepository),
            observeWeight = ObserveWeightUseCase(weightRepository),
            observeLatestLog = observeWeighLatestLog,
            observeMassUnit = ObserveWeighMassUnitUseCase(weighPrefs),
            saveMassUnit = SaveWeighMassUnitUseCase(weighPrefs),
            observeTargetWeightKg = ObserveWeighTargetWeightKgUseCase(weighPrefs),
            saveTargetWeightKg = SaveWeighTargetWeightKgUseCase(weighPrefs),
            observeJourneyStartWeightKg = ObserveWeighJourneyStartWeightKgUseCase(weighPrefs),
            saveJourneyStartWeightKg = SaveWeighJourneyStartWeightKgUseCase(weighPrefs),
            classifyBmi = ClassifyBmiUseCase(),
            mapBmiFraction = MapBmiToScaleFractionUseCase(),
            archiveCompletedWeightGoal = archiveCompletedWeightGoal,
        )

        val observeWeighLatestTwoLogs = ObserveWeighLatestTwoLogsUseCase(weighLogRepository)
        val observeWeighLatestLogForToday = ObserveWeighLatestLogForTodayUseCase(weighLogRepository)
        val computeWeightProgressDelta = ComputeWeightProgressDeltaUseCase()
        val weighGoalDetailFactory = WeighGoalDetailViewModelFactory(
            observeTall = ObserveTallUseCase(tallRepository),
            observeWeight = ObserveWeightUseCase(weightRepository),
            observeMassUnit = ObserveWeighMassUnitUseCase(weighPrefs),
            saveMassUnit = SaveWeighMassUnitUseCase(weighPrefs),
            observeTargetWeightKg = ObserveWeighTargetWeightKgUseCase(weighPrefs),
            saveTargetWeightKg = SaveWeighTargetWeightKgUseCase(weighPrefs),
            observeJourneyStartWeightKg = ObserveWeighJourneyStartWeightKgUseCase(weighPrefs),
            observeLatestTwoLogs = observeWeighLatestTwoLogs,
            observeLatestLogForToday = observeWeighLatestLogForToday,
            saveWeightProfileAndWeighLog = saveWeightProfileAndWeighLog,
            computeDelta = computeWeightProgressDelta,
            classifyBmi = ClassifyBmiUseCase(),
            mapBmiFraction = MapBmiToScaleFractionUseCase()
        )
        val observeWeighLogsLast7Days = ObserveWeighLogsLast7DaysUseCase(weighLogRepository)
        val weighHistoryFactory = WeighHistoryViewModelFactory(
            observeMassUnit = ObserveWeighMassUnitUseCase(weighPrefs),
            observeLast7 = observeWeighLogsLast7Days,
            observeFirstInstallEpochDay = observeFirstInstallEpochDay,
            buildChart = BuildWeighHistorySevenDayChartUseCase()
        )

        setContent {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(AppColors.HomeBackground)
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
                    weighTrackerFactory = weighTrackerFactory,
                    meProfileFactory = meProfileFactory,
                    rateUsFactory = rateUsFactory,
                    weighGoalDetailFactory = weighGoalDetailFactory,
                    weighHistoryFactory = weighHistoryFactory,
                    weighGoalHistoryFactory = weighGoalHistoryFactory,
                    weighGoalHistoryDetailFactory = weighGoalHistoryDetailFactory,
                    reportViewModelFactory = reportViewModelFactory,
                    ensureFirstInstallDayUseCase = ensureFirstInstallDayUseCase,
                    recordWaterAppOpenDayUseCase = recordWaterAppOpenDayUseCase,
                    observeSavedGoalMlUseCase = observeSavedGoalMl,
                    observeLocaleOnboardingCompletedUseCase = observeLocaleOnboardingCompletedUseCase,
                    markLocaleOnboardingCompletedUseCase = markLocaleOnboardingCompletedUseCase,
                )
            }
        }
    }
}