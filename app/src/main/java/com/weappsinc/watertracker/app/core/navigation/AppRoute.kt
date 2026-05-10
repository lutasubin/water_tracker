package com.weappsinc.watertracker.app.core.navigation

sealed class AppRoute(val route: String) {
    data object Splash : AppRoute("splash")
    data object Gender : AppRoute("gender")
    data object Age : AppRoute("age")
    data object Tall : AppRoute("tall")
    data object Weight : AppRoute("weight")
    data object TallEdit : AppRoute("tall_edit")
    data object WeightEdit : AppRoute("weight_edit")
    data object AgeEdit : AppRoute("age_edit")
    data object GenderEdit : AppRoute("gender_edit")
    data object Exercise : AppRoute("exercise")
    data object WaterGoal : AppRoute("water_goal")
    data object WaterGoalEdit : AppRoute("water_goal_edit")
    data object Home : AppRoute("home")
    data object Report : AppRoute("report")
    /** Cài đặt: có nút back. */
    data object Language : AppRoute("language")

    /** Onboarding sau splash: chỉ ✓ sang gender, không back. */
    data object LanguageOnboarding : AppRoute("language_onboarding")
    data object WeighGoalDetail : AppRoute("weigh_goal_detail")
    data object WeighHistory : AppRoute("weigh_history")
    data object WeighGoalHistory : AppRoute("weigh_goal_history")
    data object WeighGoalHistoryDetail : AppRoute("weigh_goal_history_detail/{goalId}") {
        fun create(goalId: Long): String = "weigh_goal_history_detail/$goalId"
    }
    data object PrivacyPolicy : AppRoute("privacy_policy")
}
