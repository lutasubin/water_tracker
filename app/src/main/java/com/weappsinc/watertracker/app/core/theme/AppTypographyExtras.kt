package com.weappsinc.watertracker.app.core.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

/** Bộ token mở rộng khi cần dùng chi tiết weight Roboto. */
object AppTypographyExtras {
    val BodyLight: TextStyle = AppTypography.BodyMedium.copy(fontWeight = FontWeight.Light)
    val BodyThin: TextStyle = AppTypography.BodyMedium.copy(fontWeight = FontWeight.Thin)
    val TitleExtraBold: TextStyle = AppTypography.Title3.copy(fontWeight = FontWeight.ExtraBold)
}
