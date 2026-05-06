package com.weappsinc.watertracker.app.core.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.weappsinc.watertracker.R

object AppTypography {
    /** Font mặc định toàn app: Roboto từ res/font. */
    private val AppFontFamily = FontFamily(
        Font(R.font.roboto_thin, weight = FontWeight.Thin),
        Font(R.font.roboto_light, weight = FontWeight.Light),
        Font(R.font.roboto_regular, weight = FontWeight.Normal),
        Font(R.font.roboto_medium, weight = FontWeight.Medium),
        Font(R.font.roboto_semibold, weight = FontWeight.SemiBold),
        Font(R.font.roboto_bold, weight = FontWeight.Bold),
        Font(R.font.roboto_extrabold, weight = FontWeight.ExtraBold),
        Font(R.font.roboto_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
        Font(R.font.roboto_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
        Font(R.font.roboto_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
        Font(R.font.roboto_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
        Font(R.font.roboto_extrabold_italic, weight = FontWeight.ExtraBold, style = FontStyle.Italic)
    )

    val Title1 = TextStyle(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )
    val Title2 = TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )
    /** Tiêu đề App bar màn Report (gần M3 titleLarge, đậm dễ đọc). */
    val ReportTopBarTitle = Title2
    val Title3 = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )
    val DisplayNumber = TextStyle(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )
    /** Mục tiêu ml / giờ nhắc trên thẻ — gọn, không phình chiều cao card. */
    val StatCardValue = TextStyle(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )
    /** Số cân trên thẻ HÔM NAY (màn chi tiết). */
    val WeighTodayCardWeight = TextStyle(
        fontSize = 36.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )
    /** Giá trị thẻ Ngày bắt đầu / Tiến triển (màn chi tiết cân). */
    val WeighDetailStatCardValue = TextStyle(
        fontSize = 28.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )
    val BodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = AppFontFamily
    )
    val BodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = AppFontFamily
    )
    val Button = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )
    /** Nút DRINK: in hoa, đậm, rõ trên nền primary. */
    val DrinkCta = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.8.sp,
        fontFamily = AppFontFamily
    )

    val WaterGoalValue = TextStyle(
        fontSize = 56.sp,
        lineHeight = 64.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )

    /** Thẻ mục tiêu nền tím — màn chi tiết (số lớn theo design). */
    val WeighDetailHeroTargetValue = TextStyle(
        fontSize = 40.sp,
        lineHeight = 44.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )
    val WeighDetailHeroGapValue = TextStyle(
        fontSize = 26.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )
    val WeighDetailHeroLabel = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
        fontFamily = AppFontFamily
    )
    /** Tiêu đề thẻ xu hướng cân (in hoa theo AppText). */
    val WeighHistoryTrendTitle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFontFamily
    )
}
