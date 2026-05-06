package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.components.massUnitShortLabel
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighHistoryRowUi

@Composable
fun WeighHistoryLogRow(
    row: WeighHistoryRowUi,
    massUnit: MassUnit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .background(AppColors.HomeCard)
            .padding(AppDimens.HomeCardInnerPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(AppColors.BmiScaleNormal),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
            Column(Modifier.padding(start = 12.dp)) {
                Text(
                    text = row.timeLabel,
                    style = AppTypography.BodyMedium,
                    color = AppColors.HomeMuted
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = row.weightValueText,
                        style = AppTypography.StatCardValue,
                        color = AppColors.HomeTitle,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = " ${massUnitShortLabel(massUnit)}",
                        style = AppTypography.BodyMedium,
                        color = AppColors.HomeMuted,
                        modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                    )
                }
            }
        }
        if (row.deltaSignedText != null) {
            val up = row.deltaIsIncrease
            val unitShort = massUnitShortLabel(massUnit)
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(if (up) AppColors.WeighHistoryDeltaIncreaseBg else AppColors.WeighHistoryDeltaDecreaseBg)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (up) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown,
                    contentDescription = null,
                    tint = if (up) AppColors.BmiScaleHigh else AppColors.BmiScaleNormal,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "${row.deltaSignedText} $unitShort",
                    style = AppTypography.BodyMedium,
                    color = if (up) AppColors.BmiScaleHigh else AppColors.BmiScaleNormal,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}
