package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighTrackerViewModel
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighTrackerViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeighTrackerScreen(
    factory: WeighTrackerViewModelFactory,
    imageLoader: ImageLoader,
    onEditTall: () -> Unit,
    onEditWeight: () -> Unit,
    onOpenWeighGoalDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: WeighTrackerViewModel = viewModel(factory = factory)
    val state by vm.uiState.collectAsState()
    var showTargetSheet by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.HomeBackground)
    ) {
        Spacer(Modifier.padding(top = AppDimens.HomeSectionSpacing))
        WeighTrackerHeader(
            massUnit = state.displayMassUnit,
            onMassUnitSelected = vm::onMassUnitSelected,
            modifier = Modifier.padding(bottom = WeighDimens.HeaderBottomSpacing)
        )
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            WeighHeightWeightCards(
                heightValue = state.heightValueText,
                weightValue = state.weightValueText,
                massUnit = state.displayMassUnit,
                imageLoader = imageLoader,
                onEditHeight = onEditTall,
                onEditWeight = onEditWeight,
                modifier = Modifier.padding(bottom = WeighDimens.BmiCardTopSpacing)
            )
            WeighBmiCard(
                bmiValueText = state.bmiValueText,
                category = state.bmiCategory,
                lowEndFraction = state.scaleLowEndFraction,
                normalEndFraction = state.scaleNormalEndFraction,
                indicatorFraction = state.bmiIndicatorFraction,
                modifier = Modifier.padding(horizontal = WeighDimens.ScreenHorizontalPadding)
            )
            WeighTargetSection(
                hasTarget = state.targetWeightKg != null && state.targetWeightKg!! > 0f,
                targetValueText = state.targetValueText,
                massUnitLabel = if (state.displayMassUnit == MassUnit.KG) {
                    AppText.UNIT_MASS_KG
                } else {
                    AppText.UNIT_MASS_LB
                },
                gapValueText = state.gapValueText,
                journeyProgressFraction = state.journeyProgressFraction,
                journeyProgressPercent = state.journeyProgressPercent,
                imageLoader = imageLoader,
                onOpenTargetSheet = { showTargetSheet = true },
                onOpenGoalDetail = onOpenWeighGoalDetail,
                modifier = Modifier.padding(top = WeighDimens.TargetSectionTopSpacing)
            )
            Spacer(Modifier.padding(bottom = AppDimens.HomeBottomNavHeight))
        }
    }
    if (showTargetSheet) {
        WeighTargetWeightSheet(
            heightCm = state.heightCm,
            massUnit = state.displayMassUnit,
            initialKg = state.targetWeightKg
                ?: (if (state.bodyWeightKg > 0f) state.bodyWeightKg else 65f),
            imageLoader = imageLoader,
            onDismiss = { showTargetSheet = false },
            onStartJourney = { kg ->
                vm.onConfirmTargetJourney(kg, state.bodyWeightKg)
                showTargetSheet = false
            }
        )
    }
}
