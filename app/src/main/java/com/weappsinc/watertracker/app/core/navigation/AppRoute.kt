package com.weappsinc.watertracker.app.core.navigation

sealed class AppRoute(val route: String) {
    data object Splash : AppRoute("splash")
    data object Gender : AppRoute("gender")
    data object Age : AppRoute("age")
    data object Tall : AppRoute("tall")
    data object Weight : AppRoute("weight")
    data object TallEdit : AppRoute("tall_edit")
    data object WeightEdit : AppRoute("weight_edit")
    data object Exercise : AppRoute("exercise")
    data object WaterGoal : AppRoute("water_goal")
    data object WaterGoalEdit : AppRoute("water_goal_edit")
    data object Home : AppRoute("home")
    data object Report : AppRoute("report")
    data object WeighGoalDetail : AppRoute("weigh_goal_detail")
    data object WeighHistory : AppRoute("weigh_history")
}
