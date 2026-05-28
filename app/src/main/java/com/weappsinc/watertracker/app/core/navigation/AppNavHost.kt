package com.weappsinc.watertracker.app.core.navigation

import android.app.Activity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weappsinc.watertracker.app.core.ads.AdsManager
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
import com.weappsinc.watertracker.app.feature.water.domain.usecase.RecordWaterAppOpenDayUseCase
import com.weappsinc.watertracker.app.feature.water.presentation.home.HomeScreen
import com.weappsinc.watertracker.app.feature.water.presentation.screen.PrivacyPolicyScreen
import com.weappsinc.watertracker.app.feature.water.presentation.screen.WaterGoalScreen
import com.weappsinc.watertracker.app.feature.water.presentation.report.ReportScreen
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.ReportViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.me.rate.RateUsViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.MeProfileViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterGoalViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModelFactory
import com.weappsinc.watertracker.app.feature.settings.presentation.screen.LanguageScreen
import com.weappsinc.watertracker.app.feature.weigh.presentation.screen.WeighGoalDetailScreen
import com.weappsinc.watertracker.app.feature.weigh.presentation.screen.WeighGoalHistoryDetailScreen
import com.weappsinc.watertracker.app.feature.weigh.presentation.screen.WeighGoalHistoryScreen
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalHistoryDetailViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalHistoryViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.screen.WeighHistoryScreen
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalDetailViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighHistoryViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighTrackerViewModelFactory
import com.weappsinc.watertracker.app.feature.weight.presentation.screen.WeightSelectionScreen
import com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel.WeightViewModelFactory
import com.weappsinc.watertracker.app.feature.settings.domain.usecase.MarkLocaleOnboardingCompletedUseCase
import com.weappsinc.watertracker.app.feature.settings.domain.usecase.ObserveLocaleOnboardingCompletedUseCase
import com.weappsinc.watertracker.app.core.theme.AppColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val SPLASH_APP_OPEN_TIMEOUT_MS = 8_000L

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
    rateUsFactory: RateUsViewModelFactory,
    weighGoalDetailFactory: WeighGoalDetailViewModelFactory,
    weighHistoryFactory: WeighHistoryViewModelFactory,
    weighGoalHistoryFactory: WeighGoalHistoryViewModelFactory,
    weighGoalHistoryDetailFactory: (Long) -> WeighGoalHistoryDetailViewModelFactory,
    reportViewModelFactory: ReportViewModelFactory,
    ensureFirstInstallDayUseCase: EnsureFirstInstallDayUseCase,
    recordWaterAppOpenDayUseCase: RecordWaterAppOpenDayUseCase,
    observeSavedGoalMlUseCase: ObserveSavedGoalMlUseCase,
    observeLocaleOnboardingCompletedUseCase: ObserveLocaleOnboardingCompletedUseCase,
    markLocaleOnboardingCompletedUseCase: MarkLocaleOnboardingCompletedUseCase,
    adsManager: AdsManager,
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val navGate = rememberNavActionGate()
    val adScope = rememberCoroutineScope()
    val savedGoalMl by observeSavedGoalMlUseCase().collectAsState(initial = null)
    val localeOnboardingDone by observeLocaleOnboardingCompletedUseCase()
        .collectAsState(initial = false)
    // Không animate giữa màn → tránh lộ nền tối khi đổi locale / pop stack rút gọn onboarding.
    NavHost(
        navController = navController,
        startDestination = AppRoute.Splash.route,
        modifier = Modifier.fillMaxSize().background(AppColors.HomeBackground),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable(AppRoute.Splash.route) {
            SplashScreen(
                onBootstrap = {
                    ensureFirstInstallDayUseCase()
                    recordWaterAppOpenDayUseCase()
                    val dm = context.resources.displayMetrics
                    val widthDp = (dm.widthPixels / dm.density).toInt().coerceAtLeast(320)
                    adsManager.warmUp(context.applicationContext)
                    adsManager.preloadAllKnownPlacements(context.applicationContext, widthDp)
                },
                onSplashFinished = {
                    val targetRoute = when {
                        (savedGoalMl ?: 0) > 0 -> AppRoute.Home.route
                        !localeOnboardingDone -> AppRoute.LanguageOnboarding.route
                        else -> AppRoute.Gender.route
                    }
                    val openTargetRoute = {
                        navGate.runAfterFullscreenAd {
                            navController.navigate(targetRoute) {
                                popUpTo(AppRoute.Splash.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                    val splashWaitStartMs = System.currentTimeMillis()
                    val adReady = adsManager.awaitAppOpenReady(SPLASH_APP_OPEN_TIMEOUT_MS)
                    val elapsedMs = System.currentTimeMillis() - splashWaitStartMs
                    val remainingMs = (SPLASH_APP_OPEN_TIMEOUT_MS - elapsedMs).coerceAtLeast(0L)
                    val activity = context as? Activity
                    if (activity != null && adReady) {
                        adsManager.showAppOpen(activity) { openTargetRoute() }
                    } else {
                        if (remainingMs > 0L) delay(remainingMs)
                        openTargetRoute()
                    }
                }
            )
        }
        composable(AppRoute.Gender.route) {
            // Ghi sau khi đã sang màn Gender — tránh ✓ Language làm đổi flow state + navigate cùng lúc (nháy đen).
            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    markLocaleOnboardingCompletedUseCase()
                }
            }
            GenderSelectionScreen(
                factory = genderFactory,
                onBack = {},
                onNext = {
                    navGate.run {
                        navController.navigate(AppRoute.Age.route) { launchSingleTop = true }
                    }
                },
            )
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
                    val openHomeAfterGoal = {
                        navGate.runAfterFullscreenAd {
                            // Xóa cả onboarding (Gender…WaterGoal); chỉ giữ Home — tránh Language pop chồng gọi rơi vào Tall/Weight/…
                            navController.navigate(AppRoute.Home.route) {
                                popUpTo(AppRoute.Gender.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                    val activity = context as? Activity
                    if (activity == null) {
                        openHomeAfterGoal()
                    } else {
                        adScope.launch {
                            adsManager.showInterstitialWhenReady(activity) { openHomeAfterGoal() }
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
                rateUsFactory = rateUsFactory,
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
                onEditAge = {
                    navGate.run {
                        navController.navigate(AppRoute.AgeEdit.route) { launchSingleTop = true }
                    }
                },
                onEditGender = {
                    navGate.run {
                        navController.navigate(AppRoute.GenderEdit.route) { launchSingleTop = true }
                    }
                },
                onOpenWeighGoalDetail = {
                    navGate.run {
                        navController.navigate(AppRoute.WeighGoalDetail.route) { launchSingleTop = true }
                    }
                },
                onOpenWeighGoalHistory = {
                    navGate.run {
                        navController.navigate(AppRoute.WeighGoalHistory.route) { launchSingleTop = true }
                    }
                },
                onOpenLanguage = {
                    navGate.run {
                        navController.navigate(AppRoute.Language.route) { launchSingleTop = true }
                    }
                },
                onOpenPrivacyPolicy = {
                    navGate.run {
                        navController.navigate(AppRoute.PrivacyPolicy.route) { launchSingleTop = true }
                    }
                },
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
                beforeApplyMain = {},
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
        composable(AppRoute.WeighGoalHistory.route) {
            WeighGoalHistoryScreen(
                factory = weighGoalHistoryFactory,
                onBack = { navGate.run { navController.popBackStack() } },
                onOpenGoalDetail = { goalId ->
                    navGate.run {
                        navController.navigate(AppRoute.WeighGoalHistoryDetail.create(goalId)) {
                            launchSingleTop = true
                        }
                    }
                },
            )
        }
        composable(
            route = AppRoute.WeighGoalHistoryDetail.route,
            arguments = listOf(navArgument("goalId") { type = NavType.LongType }),
        ) { backStackEntry ->
            val goalId = backStackEntry.arguments?.getLong("goalId") ?: 0L
            if (goalId <= 0L) {
                LaunchedEffect(Unit) { navGate.run { navController.popBackStack() } }
                return@composable
            }
            WeighGoalHistoryDetailScreen(
                factory = weighGoalHistoryDetailFactory(goalId),
                onBack = { navGate.run { navController.popBackStack() } },
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
                showFooterAd = false,
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
                showFooterAd = false,
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
        composable(AppRoute.AgeEdit.route) {
            AgeSelectionScreen(
                factory = ageFactory,
                showFooterAd = false,
                onBack = {
                    navGate.run {
                        navController.popBackStack(AppRoute.AgeEdit.route, inclusive = true)
                    }
                },
                onNext = {
                    navGate.run {
                        navController.popBackStack(AppRoute.AgeEdit.route, inclusive = true)
                    }
                }
            )
        }
        composable(AppRoute.GenderEdit.route) {
            GenderSelectionScreen(
                factory = genderFactory,
                showFooterAd = false,
                onBack = {
                    navGate.run {
                        navController.popBackStack(AppRoute.GenderEdit.route, inclusive = true)
                    }
                },
                onNext = {
                    navGate.run {
                        navController.popBackStack(AppRoute.GenderEdit.route, inclusive = true)
                    }
                },
            )
        }
        composable(AppRoute.Report.route) {
            ReportScreen(
                factory = reportViewModelFactory,
                onBack = {
                    val popReport = { navGate.runAfterFullscreenAd { navController.popBackStack() } }
                    val activity = context as? Activity
                    if (activity == null) {
                        popReport()
                    } else {
                        adScope.launch {
                            adsManager.showInterstitialWhenReady(activity) { popReport() }
                        }
                    }
                }
            )
        }
        composable(AppRoute.PrivacyPolicy.route) {
            PrivacyPolicyScreen(onBack = { navGate.run { navController.popBackStack() } })
        }
        composable(AppRoute.WaterGoalEdit.route) {
            WaterGoalScreen(
                factory = waterGoalFactoryEdit,
                viewModelKey = "water_goal_edit",
                showFooterAd = false,
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
