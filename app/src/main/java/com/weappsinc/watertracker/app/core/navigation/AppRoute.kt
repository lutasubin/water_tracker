package com.weappsinc.watertracker.app.core.navigation

sealed class AppRoute(val route: String) {
    data object Splash : AppRoute("splash")
    data object Gender : AppRoute("gender")
    data object Age : AppRoute("age")
    data object Tall : AppRoute("tall")
    data object Weight : AppRoute("weight")
    data object Exercise : AppRoute("exercise")
    data object WaterGoal : AppRoute("water_goal")
    data object WaterGoalEdit : AppRoute("water_goal_edit")
    data object Home : AppRoute("home")
}
