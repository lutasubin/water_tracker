package com.weappsinc.watertracker.app.feature.water.presentation.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.components.AppPrimaryButton
import com.weappsinc.watertracker.app.core.components.AppTopBar
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterGoalViewModel
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterGoalViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterUnit
import com.weappsinc.watertracker.app.feature.water.presentation.screen.WaterUnitToggle
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@androidx.compose.material3.ExperimentalMaterial3Api
@Composable
fun WaterGoalScreen(
    modifier: Modifier = Modifier,
    factory: WaterGoalViewModelFactory,
    onBack: () -> Unit
) {
    val vm: WaterGoalViewModel = viewModel(factory = factory)
    val baseGoalMl by vm.baseGoalMl.collectAsState()
    val adjustMl by vm.adjustMl.collectAsState()
    val unit by vm.unit.collectAsState()
    var showAdjustSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val totalGoalMl = baseGoalMl + adjustMl

    val displayValue = formatWaterAmount(totalGoalMl, unit)
    val unitLabel = if (unit == WaterUnit.ML) AppText.UNIT_ML else AppText.UNIT_L

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.SplashBackgroundSolid)
    ) {
        AsyncImage(
            model = AssetPaths.SPLASH_BACKGROUND,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppDimens.WaterGoalHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AppTopBar(
                onBack = onBack,
                containerColor = androidx.compose.ui.graphics.Color.Transparent,
                contentColor = AppColors.WaterGoalTitle
            )
            Spacer(Modifier.height(AppDimens.WaterGoalTitleSpacing))
            Text(
                text = AppText.WATER_GOAL_TITLE,
                color = AppColors.WaterGoalTitle,
                style = AppTypography.Title2
            )
            Spacer(Modifier.height(AppDimens.WaterGoalDescTopSpacing))
            Text(
                text = AppText.WATER_GOAL_DESC,
                color = AppColors.WaterGoalDesc,
                style = AppTypography.BodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(AppDimens.WaterGoalUnitToggleSpacing))
            WaterUnitToggle(
                leftText = AppText.UNIT_ML,
                rightText = AppText.UNIT_L,
                selectedLeft = unit == WaterUnit.ML,
                onLeftClick = { vm.onSelectUnit(WaterUnit.ML) },
                onRightClick = { vm.onSelectUnit(WaterUnit.L) }
            )

            Spacer(Modifier.height(AppDimens.WaterGoalBetweenValueAndDividerSpacing))

            Text(
                text = "$displayValue ${unitLabel}",
                color = AppColors.WaterGoalValue,
                style = AppTypography.WaterGoalValue
            )

            Spacer(Modifier.height(AppDimens.WaterGoalDividerHeight))
            Box(
                modifier = Modifier
                    .width(AppDimens.WaterGoalDividerWidth)
                    .height(AppDimens.WaterGoalDividerHeight)
                    .background(AppColors.WaterGoalDivider)
            )

            Spacer(Modifier.height(AppDimens.WaterGoalAdjustButtonTopSpacing))

            Button(
                onClick = { showAdjustSheet = true },
                modifier = Modifier
                    .width(AppDimens.WaterGoalAdjustButtonWidth)
                    .height(AppDimens.WaterGoalAdjustButtonHeight),
                shape = RoundedCornerShape(AppDimens.WaterGoalAdjustButtonCorner),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.AdjustButton),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text(text = "+", color = AppColors.GenderSelectedContent, style = AppTypography.Title2)
                    Spacer(Modifier.size(AppDimens.WaterGoalAdjustPlusSpacing))
                    Text(text = AppText.ADJUST, color = AppColors.GenderSelectedContent, style = AppTypography.BodyLarge)
                }
            }

            Spacer(Modifier.weight(1f))

            AppPrimaryButton(
                text = AppText.START,
                onClick = {},
                modifier = Modifier.padding(bottom = AppDimens.WaterGoalStartButtonBottomPadding),
                containerColor = AppColors.GenderSelectedContent,
                textColor = AppColors.GenderPrimary
            )
        }

        if (showAdjustSheet) {
            ModalBottomSheet(
                onDismissRequest = { showAdjustSheet = false },
                sheetState = sheetState,
                containerColor = AppColors.GenderScreenBackground
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.WaterGoalHorizontalPadding)
                        .padding(bottom = AppDimens.WaterGoalStartButtonBottomPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = AppText.WATER_GOAL_TITLE,
                            color = AppColors.GenderTitle,
                            style = AppTypography.Title3
                        )
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = AppColors.GenderTitle,
                            modifier = Modifier
                                .size(28.dp)
                                .clickable {
                                    scope.launch {
                                        sheetState.hide()
                                        showAdjustSheet = false
                                    }
                                }
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AdjustCircleButton(symbol = "-") { vm.onDecreaseAdjust() }
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = displayValue,
                                color = AppColors.GenderTitle,
                                style = AppTypography.WaterGoalValue
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = unitLabel,
                                color = AppColors.GenderTitle,
                                style = AppTypography.Title3
                            )
                        }
                        AdjustCircleButton(symbol = "+") { vm.onIncreaseAdjust() }
                    }
                    Spacer(Modifier.height(36.dp))
                    AppPrimaryButton(
                        text = AppText.SAVE,
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                                showAdjustSheet = false
                            }
                        },
                        containerColor = AppColors.GenderPrimary,
                        textColor = AppColors.GenderSelectedContent
                    )
                }
            }
        }
    }
}

@Composable
private fun AdjustCircleButton(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.GenderUnselectedBackground)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = symbol, color = AppColors.GenderPrimary, style = AppTypography.Title3)
    }
}

private fun formatWaterAmount(valueMl: Int, unit: WaterUnit): String {
    return when (unit) {
        WaterUnit.ML -> "${valueMl}"
        WaterUnit.L -> "${(valueMl / 1000f * 10f).roundToInt() / 10f}"
    }
}

