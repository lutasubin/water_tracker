package com.weappsinc.watertracker.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weappsinc.watertracker.app.feature.age.presentation.screen.AgeSelectionScreen
import com.weappsinc.watertracker.app.feature.age.presentation.viewmodel.AgeViewModelFactory
import com.weappsinc.watertracker.app.feature.gender.presentation.screen.GenderSelectionScreen
import com.weappsinc.watertracker.app.feature.gender.presentation.viewmodel.GenderViewModelFactory
import com.weappsinc.watertracker.app.feature.splash.presentation.screen.SplashScreen

@Composable
fun AppNavHost(genderFactory: GenderViewModelFactory, ageFactory: AgeViewModelFactory) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoute.Splash.route) {
        composable(AppRoute.Splash.route) {
            SplashScreen {
                navController.navigate(AppRoute.Gender.route) {
                    popUpTo(AppRoute.Splash.route) { inclusive = true }
                }
            }
        }
        composable(AppRoute.Gender.route) {
            GenderSelectionScreen(factory = genderFactory) {
                navController.navigate(AppRoute.Age.route)
            }
        }
        composable(AppRoute.Age.route) {
            AgeSelectionScreen(
                factory = ageFactory,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
