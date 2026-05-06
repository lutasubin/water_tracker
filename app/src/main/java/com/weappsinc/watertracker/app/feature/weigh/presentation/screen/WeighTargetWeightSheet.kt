package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay

private const val MIN_TARGET_KG = 30f
private const val MAX_TARGET_KG = 200f
private const val STEP_KG = 0.5f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeighTargetWeightSheet(
    heightCm: Int,
    massUnit: MassUnit,
    initialKg: Float,
    imageLoader: ImageLoader,
    onDismiss: () -> Unit,
    onStartJourney: (targetKg: Float) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var draftKg by remember { mutableFloatStateOf(initialKg.coerceIn(MIN_TARGET_KG, MAX_TARGET_KG)) }
    LaunchedEffect(initialKg) { draftKg = initialKg.coerceIn(MIN_TARGET_KG, MAX_TARGET_KG) }
    val unitLabel = if (massUnit == MassUnit.KG) AppText.UNIT_MASS_KG else AppText.UNIT_MASS_LB
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppColors.HomeCard
    ) {
        Column(Modifier.padding(horizontal = 20.dp).padding(bottom = 28.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = AssetPaths.GOAL_ICON,
                    contentDescription = null,
                    imageLoader = imageLoader,
                    modifier = Modifier.size(WeighDimens.SheetHeaderIconSize),
                    contentScale = ContentScale.Fit
                )
                Text(
                    AppText.TARGET_SHEET_HEADER_TITLE,
                    Modifier.weight(1f).padding(horizontal = 10.dp),
                    style = AppTypography.Title3,
                    color = AppColors.HomeTitle
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Filled.Close, AppText.CLOSE, tint = AppColors.HomeMuted)
                }
            }
            Spacer(Modifier.height(WeighDimens.SheetInnerSpacing))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepperChip(Icons.Filled.KeyboardArrowLeft) {
                    draftKg = MassDisplay.snapTargetKg((draftKg - STEP_KG).coerceIn(MIN_TARGET_KG, MAX_TARGET_KG))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        MassDisplay.formatTargetKg(draftKg, massUnit),
                        style = AppTypography.StatCardValue,
                        color = AppColors.HomeTitle,
                        textAlign = TextAlign.Center
                    )
                    Text(unitLabel, style = AppTypography.BodyMedium, color = AppColors.HomeMuted)
                }
                StepperChip(Icons.Filled.KeyboardArrowRight) {
                    draftKg = MassDisplay.snapTargetKg((draftKg + STEP_KG).coerceIn(MIN_TARGET_KG, MAX_TARGET_KG))
                }
            }
            Spacer(Modifier.height(WeighDimens.SheetInnerSpacing))
            WeighTargetSheetExpectedBmiCard(heightCm, draftKg)
            Spacer(Modifier.height(WeighDimens.SheetInnerSpacing))
            Button(
                onClick = { onStartJourney(MassDisplay.snapTargetKg(draftKg)) },
                modifier = Modifier.fillMaxWidth().height(WeighDimens.SheetCtaHeight),
                shape = RoundedCornerShape(WeighDimens.SheetCtaHeight / 2),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.WeighJourneyCta)
            ) {
                Text(AppText.START_JOURNEY, style = AppTypography.Button, color = AppColors.GenderSelectedContent)
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    null,
                    Modifier.padding(start = 8.dp),
                    tint = AppColors.GenderSelectedContent
                )
            }
        }
    }
}

@Composable
private fun StepperChip(icon: ImageVector, onClick: () -> Unit) {
    Box(
        Modifier
            .size(WeighDimens.SheetStepperButtonSize)
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.WeighSheetStepperBg)
            .clickable(onClick = onClick),
        Alignment.Center
    ) {
        Icon(icon, null, tint = AppColors.WeighSheetStepperIcon)
    }
}
