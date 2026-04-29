package com.weappsinc.watertracker.app.core.navigation

sealed class AppRoute(val route: String) {
    data object Splash : AppRoute("splash")
    data object Gender : AppRoute("gender")
    data object Age : AppRoute("age")
}
