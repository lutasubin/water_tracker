package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import com.weappsinc.watertracker.R
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
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppColors.HomeCard
    ) {
        Column(Modifier.padding(horizontal = 20.dp).padding(bottom = 28.dp)) {
            WeighTargetWeightSheetHeader(imageLoader = imageLoader, onDismiss = onDismiss)
            Spacer(Modifier.height(WeighDimens.SheetInnerSpacing))
            WeighTargetWeightPickerRow(
                draftKg = draftKg,
                massUnit = massUnit,
                onDecrement = {
                    draftKg = MassDisplay.snapTargetKg((draftKg - STEP_KG).coerceIn(MIN_TARGET_KG, MAX_TARGET_KG))
                },
                onIncrement = {
                    draftKg = MassDisplay.snapTargetKg((draftKg + STEP_KG).coerceIn(MIN_TARGET_KG, MAX_TARGET_KG))
                }
            )
            Spacer(Modifier.height(WeighDimens.SheetInnerSpacing))
            WeighTargetSheetExpectedBmiCard(heightCm, draftKg)
            Spacer(Modifier.height(WeighDimens.SheetInnerSpacing))
            Button(
                onClick = { onStartJourney(MassDisplay.snapTargetKg(draftKg)) },
                modifier = Modifier.fillMaxWidth().height(WeighDimens.SheetCtaHeight),
                shape = RoundedCornerShape(WeighDimens.SheetCtaHeight / 2),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.WeighJourneyCta)
            ) {
                Text(stringResource(R.string.start_journey), style = AppTypography.Button, color = AppColors.GenderSelectedContent)
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
