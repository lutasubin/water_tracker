package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.weappsinc.watertracker.app.core.components.AppUnitToggle
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalDetailViewModel
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighGoalDetailViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeighGoalDetailScreen(
    factory: WeighGoalDetailViewModelFactory,
    onClose: () -> Unit,
    onOpenHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: WeighGoalDetailViewModel = viewModel(factory = factory)
    val state by vm.uiState.collectAsState()
    var showTargetSheet by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context).components { add(SvgDecoder.Factory()) }.build()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.HomeBackground)
    ) {
        Spacer(Modifier.padding(top = AppDimens.HomeSectionSpacing))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = WeighDimens.ScreenHorizontalPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppUnitToggle(
                leftText = AppText.UNIT_MASS_KG,
                rightText = AppText.UNIT_MASS_LB,
                isLeftSelected = state.displayMassUnit == MassUnit.KG,
                onLeftClick = { vm.onMassUnitSelected(MassUnit.KG) },
                onRightClick = { vm.onMassUnitSelected(MassUnit.LB) }
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.Close, contentDescription = AppText.CLOSE, tint = AppColors.HomeTitle)
            }
        }
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = WeighDimens.ScreenHorizontalPadding)
        ) {
            Spacer(Modifier.padding(top = 16.dp))
            WeighGoalDetailHeroCard(
                targetValueText = state.targetValueText,
                massUnitLabel = state.massUnitLabel,
                gapValueText = state.gapValueText,
                journeyProgressFraction = state.journeyProgressFraction,
                journeyProgressPercent = state.journeyProgressPercent,
                imageLoader = imageLoader,
                onEditTarget = { showTargetSheet = true }
            )
            Spacer(Modifier.padding(top = 16.dp))
            WeighGoalDetailStatsRow(
                startWeightText = state.startWeightText,
                massUnitLabel = state.massUnitLabel,
                progressDeltaValueText = state.progressDeltaValueText,
                progressDeltaFavorable = state.progressDeltaFavorable,
                progressDeltaNeutral = state.progressDeltaNeutral
            )
            Spacer(Modifier.padding(top = 16.dp))
            WeighGoalDetailTodayCard(
                displayDraftKg = state.displayDraftKg,
                massUnit = state.displayMassUnit,
                massUnitLabel = state.massUnitLabel,
                showWeighRecordCta = state.showWeighRecordCta,
                savedBannerTime = state.savedBannerTime,
                recordError = state.recordError,
                onStepKg = vm::onDraftStep,
                onRecord = { vm.onRecordWeight(state.displayDraftKg) },
                onHistoryClick = onOpenHistory
            )
            Spacer(Modifier.padding(top = WeighDimens.BmiCardTopSpacing))
            WeighBmiCard(
                bmiValueText = state.bmiValueText,
                category = state.bmiCategory,
                lowEndFraction = state.scaleLowEndFraction,
                normalEndFraction = state.scaleNormalEndFraction,
                indicatorFraction = state.bmiIndicatorFraction
            )
            Spacer(Modifier.padding(bottom = AppDimens.HomeBottomNavHeight))
        }
    }
    if (showTargetSheet) {
        WeighTargetWeightSheet(
            heightCm = state.heightCm,
            massUnit = state.displayMassUnit,
            initialKg = state.targetWeightKg ?: state.displayDraftKg,
            imageLoader = imageLoader,
            onDismiss = { showTargetSheet = false },
            onStartJourney = { kg ->
                vm.onSaveTargetOnly(kg)
                showTargetSheet = false
            }
        )
    }
}
