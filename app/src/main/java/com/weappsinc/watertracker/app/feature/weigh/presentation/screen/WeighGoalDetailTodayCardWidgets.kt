package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

/** Khối con thẻ HÔM NAY (header, stepper, nút ghi, banner đã lưu). */
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens

@Composable
internal fun WeighTodayCardHeaderRow(onHistoryClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(WeighDimens.WeighTodayStatusRingSize),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(2.dp, AppColors.BmiScaleNormal, CircleShape)
                )
                Box(
                    Modifier
                        .size(WeighDimens.WeighTodayStatusDotSize)
                        .clip(CircleShape)
                        .background(AppColors.BmiScaleNormal)
                )
            }
            Spacer(Modifier.size(8.dp))
            Text(
                AppText.WEIGH_GOAL_DETAIL_TODAY,
                style = AppTypography.Title3,
                color = AppColors.HomeTitle,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = AppText.WEIGH_GOAL_DETAIL_HISTORY,
            style = AppTypography.BodyMedium.copy(fontWeight = FontWeight.Bold),
            color = AppColors.WeighHistoryAccent,
            modifier = Modifier.clickable(onClick = onHistoryClick).padding(4.dp)
        )
    }
}

@Composable
internal fun WeighTodayWeightStepperRow(
    weightText: String,
    massUnitLabel: String,
    onStepMinus: () -> Unit,
    onStepPlus: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WeighTodaySquareStepper(onClick = onStepMinus) {
            Icon(
                Icons.Filled.Remove,
                null,
                tint = AppColors.WeighTodayStepperGlyph,
                modifier = Modifier.size(WeighDimens.WeighTodayStepperIconSize)
            )
        }
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                weightText,
                style = AppTypography.WeighTodayCardWeight,
                color = AppColors.HomeTitle
            )
            Text(
                text = " $massUnitLabel",
                style = AppTypography.BodyMedium,
                color = AppColors.HomeMuted,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        WeighTodaySquareStepper(onClick = onStepPlus) {
            Icon(
                Icons.Filled.Add,
                null,
                tint = AppColors.WeighTodayStepperGlyph,
                modifier = Modifier.size(WeighDimens.WeighTodayStepperIconSize)
            )
        }
    }
}

@Composable
private fun WeighTodaySquareStepper(onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        Modifier
            .size(WeighDimens.WeighTodayStepperSize)
            .clip(RoundedCornerShape(WeighDimens.WeighTodayStepperCorner))
            .background(AppColors.WeighTodayStepperSurface)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
internal fun WeighTodayRecordPillButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(AppDimens.AgeButtonHeight),
        shape = RoundedCornerShape(29.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AppColors.WeighHistoryAccent)
    ) {
        Text(
            text = AppText.WEIGH_GOAL_DETAIL_RECORD,
            color = Color.White,
            style = AppTypography.BodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
internal fun WeighTodaySavedBanner(timeText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(AppColors.WeighSavedBannerBg)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(AppColors.BmiScaleNormal),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(Modifier.size(10.dp))
        Column {
            Text(
                text = AppText.WEIGH_GOAL_DETAIL_SAVED,
                style = AppTypography.BodyLarge,
                fontWeight = FontWeight.Bold,
                color = AppColors.WeighSavedBannerText
            )
            Text(
                text = timeText,
                style = AppTypography.BodyMedium,
                color = AppColors.HomeMuted
            )
        }
    }
}
