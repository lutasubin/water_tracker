package com.weappsinc.watertracker.app.core.theme

import androidx.compose.ui.graphics.Color

object AppColors {
    val SplashBackgroundSolid = Color(0xFF1E5AE8)
    val SplashTitle = Color.White
    val SplashProgress = Color.White
    val SplashProgressTrack = Color(0xFF0C41AF)

    val GenderScreenBackground = Color(0xFFFFFFFF)
    val GenderPrimary = Color(0xFF1855D8)
    val GenderUnselectedBackground = Color(0xFFF3F7FF)
    val GenderSelectedContent = Color.White
    val GenderUnselectedContent = Color(0xFF8D9BB5)
    val GenderTitle = Color(0xFF25314D)

    // Goal screen UI
    val AdjustButton = Color(0xFF4B7EEB)

    val WaterGoalTitle = Color.White
    val WaterGoalDesc = Color(0xB3FFFFFF) // ~70% opacity
    val WaterGoalValue = Color.White
    val WaterGoalDivider = Color(0x33FFFFFF) // ~20% opacity

    val HomeBackground = Color(0xFFF5F8FF)
    val HomePrimary = Color(0xFF1D5DDA)
    val HomeTitle = Color(0xFF1A2B4A)
    val HomeSecondaryText = Color(0xFF5B7CCE)
    val HomeMuted = Color(0xFF8D9BB5)
    val HomeCard = Color(0xFFFFFFFF)
    val HomeProgressTrack = Color(0xFFE0E7F5)
    val HomeStreakPillBg = Color(0xFFFFFFFF)
    /** Chữ trong pill streak (xám đậm, không dùng primary). */
    val HomeStreakPillText = Color(0xFF4A5568)
    /** Phần trăm tiến độ bên phải — xanh primary theo design. */
    val HomeProgressPercentText = HomePrimary
    val HomeNavInactive = Color(0xFF8D9BB5)
    /** Nền ô bút sửa trên thẻ Mục tiêu (xanh nhạt). */
    val HomeStatEditButtonBg = Color(0xFFE8EEFC)
}
