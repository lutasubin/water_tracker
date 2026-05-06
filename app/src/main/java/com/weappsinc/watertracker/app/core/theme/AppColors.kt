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
    /** Nền nút pager dưới biểu đồ Month (xanh nhạt). */
    val ReportPagerButtonBackground = Color(0xFFEBF2FF)
    /** Vạch nền (track) thanh cuộn pager — xanh rất nhạt. */
    val ReportPagerTrack = Color(0xFFDDE8FA)

    val BmiScaleLow = Color(0xFFF3A641)
    val BmiScaleNormal = Color(0xFF11CF74)
    val BmiScaleHigh = Color(0xFFD92030)
    val BmiIndicatorFill = Color.White
    val BmiIndicatorStroke = Color.Black
    val BmiBadgeUnderBg = Color(0xFFFFF4E8)
    val BmiBadgeUnderText = BmiScaleLow
    val BmiBadgeNormalBg = Color(0xFFE8FBF2)
    val BmiBadgeNormalText = BmiScaleNormal
    val BmiBadgeOverBg = Color(0xFFFDEBEC)
    val BmiBadgeOverText = BmiScaleHigh

    val WeighExpectedBmiCardBg = Color(0xFFF0F3F8)
    val WeighJourneyCta = Color(0xFF5D5FEF)
    val WeighJourneyProgressTrack = Color(0xFFE8EAF0)
    val WeighJourneyProgressFill = Color(0xFF5D5FEF)
    val WeighSheetStepperBg = Color(0xFFE8EBF0)
    val WeighSheetStepperIcon = Color(0xFF3C4043)
    /** Nhãn phụ thẻ mục tiêu (CÁCH BIỆT, TIẾN ĐỘ…). */
    val WeighGoalLabelMuted = Color(0xFF8E92A3)
    /** Tiến triển cân: hướng có lợi với mục tiêu (giảm hoặc tăng tùy ngữ cảnh). */
    val WeighProgressDeltaFavorable = BmiScaleNormal
    /** Tiến triển cân: hướng không lợi với mục tiêu. */
    val WeighProgressDeltaUnfavorable = BmiScaleHigh
    /** Nền thẻ MỤC TIÊU trên cùng (màn chi tiết cân). */
    val WeighGoalDetailHeroBg = Color(0xFF504AE0)
    val WeighSavedBannerBg = Color(0xFFE8FBF2)
    val WeighSavedBannerText = BmiScaleNormal
    /** Accent lịch sử cân / liên kết (#504AE0). */
    val WeighHistoryAccent = Color(0xFF504AE0)
    val WeighHistoryBadgeBg = Color(0xFFEAE9FC)
    /** Đỉnh gradient vùng dưới đường biểu đồ xu hướng cân (tím nhạt). */
    val WeighHistoryChartFillTop = Color(0x33504AE0)
    val WeighHistoryDeltaIncreaseBg = Color(0xFFFDEBEC)
    val WeighHistoryDeltaDecreaseBg = Color(0xFFE8FBF2)

    /** Thanh tiến độ trên thẻ mục tiêu tím (màn chi tiết). */
    val WeighDetailHeroProgressTrack = Color(0x4DFFFFFF)
    val WeighDetailHeroProgressFill = Color.White
    val WeighDetailHeroEditButtonBg = Color.White
    val WeighDetailHeroEditIcon = WeighHistoryAccent
    val WeighTodayStepperSurface = Color(0xFFE8EDF6)
    val WeighTodayStepperGlyph = WeighHistoryAccent
}
