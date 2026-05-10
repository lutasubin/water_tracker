package com.weappsinc.watertracker.app.core.constants

object AssetPaths {
    private const val ASSET_BASE = "file:///android_asset"
    private const val FLAGS_PATH = "$ASSET_BASE/flags"

    const val SPLASH_BACKGROUND = "$ASSET_BASE/images/splash_background.png"
    const val SPLASH_ICON = "$ASSET_BASE/svg/icon_splash.svg"
    const val MALE_ICON = "$ASSET_BASE/svg/male_icon.svg"
    const val FEMALE_ICON = "$ASSET_BASE/svg/female_icon.svg"
    const val EXERCISE_LOW_ICON = "$ASSET_BASE/svg/icon1.svg"
    const val EXERCISE_MODERATE_ICON = "$ASSET_BASE/svg/icon3.svg"
    const val EXERCISE_HIGH_ICON = "$ASSET_BASE/svg/icon2.svg"

    const val GOAL_ICON = "$ASSET_BASE/svg/goal_icon.svg"
    const val NOTIE_ICON = "$ASSET_BASE/svg/notie_icon.svg"
    const val REPORT_ICON = "$ASSET_BASE/svg/report_icon.svg"
    /** Lottie ngọn lửa streak (tab Me, pill màn Water). Đường dẫn tương đối thư mục assets. */
    const val STREAK_FIRE_LOTTIE = "lottie/Fire.json"
    /** Lottie chúc mừng đạt mục tiêu uống nước trong ngày (tab Water). */
    const val FIREWORKS_LOTTIE = "lottie/fireworks.json"
    const val HOME_WATER_ICON = "$ASSET_BASE/svg/home_water_icon.svg"
    const val HOME_BMI_ICON = "$ASSET_BASE/svg/home_bmi_icon.svg"
    const val HOME_INFOR_ICON = "$ASSET_BASE/svg/home_infor_icon.svg"
    const val CELEBRATION_ICON = "$ASSET_BASE/svg/🎉.svg"
    const val DRINK_150_ICON = "$ASSET_BASE/svg/150_icon.svg"
    const val DRINK_200_ICON = "$ASSET_BASE/svg/200_icon.svg"
    const val DRINK_500_ICON = "$ASSET_BASE/svg/500_icon.svg"
    const val DRINK_ASSET = "$ASSET_BASE/svg/drink.svg"

    /** Minh họa sheet đánh giá theo số sao (rate_0 … rate_5). */
    const val RATE_0_SVG = "$ASSET_BASE/svg/rate_0.svg"
    const val RATE_1_SVG = "$ASSET_BASE/svg/rate_1.svg"
    const val RATE_2_SVG = "$ASSET_BASE/svg/rate_2.svg"
    const val RATE_3_SVG = "$ASSET_BASE/svg/rate_3.svg"
    const val RATE_4_SVG = "$ASSET_BASE/svg/rate_4.svg"
    const val RATE_5_SVG = "$ASSET_BASE/svg/rate_5.svg"

    /** Đường asset SVG tương ứng số sao đã chọn (0–5). */
    fun rateSheetSvg(stars: Int): String = when (stars.coerceIn(0, 5)) {
        0 -> RATE_0_SVG
        1 -> RATE_1_SVG
        2 -> RATE_2_SVG
        3 -> RATE_3_SVG
        4 -> RATE_4_SVG
        else -> RATE_5_SVG
    }

    /** Icon chiều cao (tab Weight + tab Me). */
    const val HEIGHT_ICON = "$ASSET_BASE/svg/height_icon.svg"
    /** Icon cân nặng (tab Weight + tab Me). */
    const val WEIGHT_ICON = "$ASSET_BASE/svg/weight_icon.svg"
    const val AGE_ICON = "$ASSET_BASE/svg/age_icon.svg"
    const val SEX_ICON = "$ASSET_BASE/svg/sex_icon.svg"
    const val NO_GOAL_ICON = "$ASSET_BASE/svg/no_goal_icon.svg"

    /** Cờ ngôn ngữ: tên file trong thư mục assets/flags (vd. vn.png). */
    fun flagAsset(flagFileName: String) = "$FLAGS_PATH/$flagFileName"
}
