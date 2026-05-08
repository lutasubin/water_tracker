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
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.MeProfileViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterGoalViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModelFactory
import com.weappsinc.watertracker.app.feature.settings.presentation.screen.LanguageScreen
import com.weappsinc.watertracker.app.feature.weigh.presentation.screen.WeighGoalDetailScreen
import com.weappsinc.watertracker.app.feature.weigh.presentation.screen.WeighHistoryScreen
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalDetailViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighHistoryViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighTrackerViewModelFactory
import com.weappsinc.watertracker.app.feature.weight.presentation.screen.WeightSelectionScreen
import com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel.WeightViewModelFactory
import com.weappsinc.watertracker.app.feature.settings.domain.usecase.MarkLocaleOnboardingCompletedUseCase
import com.weappsinc.watertracker.app.feature.settings.domain.usecase.ObserveLocaleOnboardingCompletedUseCase

// Mọi điều hướng từ UI đều qua navGate để tránh nhấp đôi gây stack/pop lệch (màn trắng).

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
    weighTrackerFactory: WeighTrackerViewModelFactory,
    meProfileFactory: MeProfileViewModelFactory,
    weighGoalDetailFactory: WeighGoalDetailViewModelFactory,
    weighHistoryFactory: WeighHistoryViewModelFactory,
    reportViewModelFactory: ReportViewModelFactory,
    ensureFirstInstallDayUseCase: EnsureFirstInstallDayUseCase,
    observeSavedGoalMlUseCase: ObserveSavedGoalMlUseCase,
    observeLocaleOnboardingCompletedUseCase: ObserveLocaleOnboardingCompletedUseCase,
    markLocaleOnboardingCompletedUseCase: MarkLocaleOnboardingCompletedUseCase,
) {
    val navController = rememberNavController()
    val navGate = rememberNavActionGate()
    val savedGoalMl by observeSavedGoalMlUseCase().collectAsState(initial = null)
    val localeOnboardingDone by observeLocaleOnboardingCompletedUseCase()
        .collectAsState(initial = false)
    NavHost(navController = navController, startDestination = AppRoute.Splash.route) {
        composable(AppRoute.Splash.route) {
            SplashScreen(
                onBootstrap = { ensureFirstInstallDayUseCase() },
                onSplashFinished = {
                    val targetRoute = when {
                        (savedGoalMl ?: 0) > 0 -> AppRoute.Home.route
                        !localeOnboardingDone -> AppRoute.LanguageOnboarding.route
                        else -> AppRoute.Gender.route
                    }
                    navGate.run {
                        navController.navigate(targetRoute) {
                            popUpTo(AppRoute.Splash.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
        composable(AppRoute.Gender.route) {
            GenderSelectionScreen(factory = genderFactory) {
                navGate.run {
                    navController.navigate(AppRoute.Age.route) { launchSingleTop = true }
                }
            }
        }
        composable(AppRoute.Age.route) {
            AgeSelectionScreen(
                factory = ageFactory,
                onBack = { navGate.run { navController.popBackStack() } },
                onNext = {
                    navGate.run {
                        navController.navigate(AppRoute.Tall.route) { launchSingleTop = true }
                    }
                }
            )
        }
        composable(AppRoute.Tall.route) {
            TallSelectionScreen(
                factory = tallFactory,
                onBack = { navGate.run { navController.popBackStack() } },
                onNext = {
                    navGate.run {
                        navController.navigate(AppRoute.Weight.route) { launchSingleTop = true }
                    }
                }
            )
        }
        composable(AppRoute.Weight.route) {
            WeightSelectionScreen(
                factory = weightFactory,
                onBack = { navGate.run { navController.popBackStack() } },
                onNext = {
                    navGate.run {
                        navController.navigate(AppRoute.Exercise.route) { launchSingleTop = true }
                    }
                }
            )
        }
        composable(AppRoute.Exercise.route) {
            ExerciseSelectionScreen(
                factory = exerciseFactory,
                onBack = { navGate.run { navController.popBackStack() } },
                onNext = {
                    navGate.run {
                        navController.navigate(AppRoute.WaterGoal.route) { launchSingleTop = true }
                    }
                }
            )
        }
        composable(AppRoute.WaterGoal.route) {
            WaterGoalScreen(
                factory = waterGoalFactoryOnboarding,
                viewModelKey = "water_goal_onboarding",
                onBack = { navGate.run { navController.popBackStack() } },
                onStartComplete = {
                    navGate.run {
                        // Xóa cả onboarding (Gender…WaterGoal); chỉ giữ Home — tránh Language pop chồng gọi rơi vào Tall/Weight/…
                        navController.navigate(AppRoute.Home.route) {
                            popUpTo(AppRoute.Gender.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
        composable(AppRoute.Home.route) {
            HomeScreen(
                waterTrackerFactory = waterTrackerFactory,
                weighTrackerFactory = weighTrackerFactory,
                meProfileFactory = meProfileFactory,
                onEditWaterGoal = {
                    navGate.run {
                        navController.navigate(AppRoute.WaterGoalEdit.route) { launchSingleTop = true }
                    }
                },
                onOpenReport = {
                    navGate.run {
                        navController.navigate(AppRoute.Report.route) { launchSingleTop = true }
                    }
                },
                onEditTall = {
                    navGate.run {
                        navController.navigate(AppRoute.TallEdit.route) { launchSingleTop = true }
                    }
                },
                onEditWeight = {
                    navGate.run {
                        navController.navigate(AppRoute.WeightEdit.route) { launchSingleTop = true }
                    }
                },
                onOpenWeighGoalDetail = {
                    navGate.run {
                        navController.navigate(AppRoute.WeighGoalDetail.route) { launchSingleTop = true }
                    }
                },
                onOpenLanguage = {
                    navGate.run {
                        navController.navigate(AppRoute.Language.route) { launchSingleTop = true }
                    }
                }
            )
        }
        composable(AppRoute.Language.route) {
            LanguageScreen(
                showBackButton = true,
                onBack = {
                    navGate.run {
                        navController.popBackStack(AppRoute.Language.route, inclusive = true)
                    }
                },
                beforeApplyMain = {},
                onApplied = {
                    navGate.run {
                        navController.popBackStack(AppRoute.Language.route, inclusive = true)
                    }
                },
            )
        }
        composable(AppRoute.LanguageOnboarding.route) {
            LanguageScreen(
                showBackButton = false,
                onBack = {},
                beforeApplyMain = { markLocaleOnboardingCompletedUseCase() },
                onApplied = {
                    navGate.run {
                        navController.navigate(AppRoute.Gender.route) {
                            popUpTo(AppRoute.LanguageOnboarding.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
            )
        }
        composable(AppRoute.WeighGoalDetail.route) {
            WeighGoalDetailScreen(
                factory = weighGoalDetailFactory,
                onClose = { navGate.run { navController.popBackStack() } },
                onOpenHistory = {
                    navGate.run {
                        navController.navigate(AppRoute.WeighHistory.route) { launchSingleTop = true }
                    }
                }
            )
        }
        composable(AppRoute.WeighHistory.route) {
            WeighHistoryScreen(
                factory = weighHistoryFactory,
                onBack = { navGate.run { navController.popBackStack() } }
            )
        }
        composable(AppRoute.TallEdit.route) {
            TallSelectionScreen(
                factory = tallFactory,
                // pop theo route: nhấp back hai lần không pop nhầm Home.
                onBack = {
                    navGate.run {
                        navController.popBackStack(AppRoute.TallEdit.route, inclusive = true)
                    }
                },
                onNext = {
                    navGate.run {
                        navController.popBackStack(AppRoute.TallEdit.route, inclusive = true)
                    }
                }
            )
        }
        composable(AppRoute.WeightEdit.route) {
            WeightSelectionScreen(
                factory = weightFactory,
                onBack = {
                    navGate.run {
                        navController.popBackStack(AppRoute.WeightEdit.route, inclusive = true)
                    }
                },
                onNext = {
                    navGate.run {
                        navController.popBackStack(AppRoute.WeightEdit.route, inclusive = true)
                    }
                }
            )
        }
        composable(AppRoute.Report.route) {
            ReportScreen(
                factory = reportViewModelFactory,
                onBack = { navGate.run { navController.popBackStack() } }
            )
        }
        composable(AppRoute.WaterGoalEdit.route) {
            WaterGoalScreen(
                factory = waterGoalFactoryEdit,
                viewModelKey = "water_goal_edit",
                onBack = { navGate.run { navController.popBackStack() } },
                onStartComplete = {
                    navGate.run {
                        navController.popBackStack(
                            AppRoute.WaterGoalEdit.route,
                            inclusive = true,
                        )
                    }
                }
            )
        }
    }
}
