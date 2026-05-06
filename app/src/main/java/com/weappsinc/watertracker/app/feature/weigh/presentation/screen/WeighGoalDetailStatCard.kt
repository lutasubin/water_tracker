package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/**
 * Thẻ trắng Ngày bắt đầu / Tiến triển: header icon + nhãn trái, số + đơn vị cùng hàng căn giữa.
 */
@Composable
fun WeighGoalDetailStatCard(
    icon: ImageVector,
    iconTint: Color,
    label: String,
    valueText: String,
    valueColor: Color,
    massUnitLabel: String,
    modifier: Modifier = Modifier,
) {
    val showUnit = valueText != "--"
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .background(AppColors.HomeCard)
            .padding(AppDimens.HomeCardInnerPadding)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text(
                text = label,
                color = AppColors.HomeMuted,
                style = AppTypography.BodyMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = valueText,
                color = valueColor,
                style = AppTypography.WeighDetailStatCardValue,
            )
            if (showUnit) {
                Text(
                    text = " $massUnitLabel",
                    color = AppColors.HomeMuted,
                    style = AppTypography.BodyMedium,
                    modifier = Modifier.padding(bottom = 2.dp),
                )
            }
        }
    }
}
