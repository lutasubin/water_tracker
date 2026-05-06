package com.weappsinc.watertracker.app.core.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weappsinc.watertracker.app.feature.age.presentation.screen.AgeSelectionScreen
import com.weappsinc.watertracker.app.feature.age.presentation.viewmodel.AgeViewModelFactory
import com.weappsinc.watertracker.app.feature.exercise.presentation.screen.ExerciseSelectionScreen
import com.weappsinc.watertracker.app.feature.exercise.presentation.viewmodel.ExerciseSelectionViewModelFactory
import com.weappsinc.watertracker.app.feature.gender.presentation.screen.GenderSelectionScreen
import com.weappsinc.watertracker.app.feature.gender.presentation.viewmodel.GenderViewModelFactory
import com.weappsinc.watertracker.app.feature.splash.presentation.screen.SplashScreen
import com.weappsinc.watertracker.app.feature.tall.presentation.screen.TallSelectionScreen
import com.weappsinc.watertracker.app.feature.tall.presentation.viewmodel.TallViewModelFactory
import com.weappsinc.watertracker.app.feature.water.domain.usecase.EnsureFirstInstallDayUseCase
import com.weappsinc.watertracker.app.feature.water.domain.usecase.ObserveSavedGoalMlUseCase
import com.weappsinc.watertracker.app.feature.water.presentation.home.HomeScreen
import com.weappsinc.watertracker.app.feature.water.presentation.screen.WaterGoalScreen
import com.weappsinc.watertracker.app.feature.water.presentation.report.ReportScreen
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.ReportViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterGoalViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModelFactory
import com.weappsinc.watertracker.app.feature.weight.presentation.screen.WeightSelectionScreen
import com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel.WeightViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    genderFactory: GenderViewModelFactory,
    ageFactory: AgeViewModelFactory,
    tallFactory: TallViewModelFactory,
    weightFactory: WeightViewModelFactory,
    exerciseFactory: ExerciseSelectionViewModelFactory,
    waterGoalFactoryOnboarding: WaterGoalViewModelFactory,
    waterGoalFactoryEdit: WaterGoalViewModelFactory,
    waterTrackerFactory: WaterTrackerViewModelFactory,
    reportViewModelFactory: ReportViewModelFactory,
    ensureFirstInstallDayUseCase: EnsureFirstInstallDayUseCase,
    observeSavedGoalMlUseCase: ObserveSavedGoalMlUseCase
) {
    val navController = rememberNavController()
    val savedGoalMl by observeSavedGoalMlUseCase().collectAsState(initial = null)
    NavHost(navController = navController, startDestination = AppRoute.Splash.route) {
        composable(AppRoute.Splash.route) {
            SplashScreen(
                onBootstrap = { ensureFirstInstallDayUseCase() },
                onSplashFinished = {
                    val targetRoute =
                        if ((savedGoalMl ?: 0) > 0) AppRoute.Home.route else AppRoute.Gender.route
                    navController.navigate(targetRoute) {
                        popUpTo(AppRoute.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(AppRoute.Gender.route) {
            GenderSelectionScreen(factory = genderFactory) {
                navController.navigate(AppRoute.Age.route)
            }
        }
        composable(AppRoute.Age.route) {
            AgeSelectionScreen(
                factory = ageFactory,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppRoute.Tall.route) }
            )
        }
        composable(AppRoute.Tall.route) {
            TallSelectionScreen(
                factory = tallFactory,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppRoute.Weight.route) }
            )
        }
        composable(AppRoute.Weight.route) {
            WeightSelectionScreen(
                factory = weightFactory,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppRoute.Exercise.route) }
            )
        }
        composable(AppRoute.Exercise.route) {
            ExerciseSelectionScreen(
                factory = exerciseFactory,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppRoute.WaterGoal.route) }
            )
        }
        composable(AppRoute.WaterGoal.route) {
            WaterGoalScreen(
                factory = waterGoalFactoryOnboarding,
                viewModelKey = "water_goal_onboarding",
                onBack = { navController.popBackStack() },
                onStartComplete = {
                    navController.navigate(AppRoute.Home.route) {
                        popUpTo(AppRoute.WaterGoal.route) { inclusive = true }
                    }
                }
            )
        }
        composable(AppRoute.Home.route) {
            HomeScreen(
                waterTrackerFactory = waterTrackerFactory,
                onEditWaterGoal = { navController.navigate(AppRoute.WaterGoalEdit.route) },
                onOpenReport = { navController.navigate(AppRoute.Report.route) }
            )
        }
        composable(AppRoute.Report.route) {
            ReportScreen(
                factory = reportViewModelFactory,
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoute.WaterGoalEdit.route) {
            WaterGoalScreen(
                factory = waterGoalFactoryEdit,
                viewModelKey = "water_goal_edit",
                onBack = { navController.popBackStack() },
                onStartComplete = { navController.popBackStack() }
            )
        }
    }
}
