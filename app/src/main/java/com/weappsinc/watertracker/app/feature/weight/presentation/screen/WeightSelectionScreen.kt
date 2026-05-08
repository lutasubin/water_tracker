package com.weappsinc.watertracker.app.feature.weight.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.components.AppPrimaryButton
import com.weappsinc.watertracker.app.core.components.AppTopBar
import com.weappsinc.watertracker.app.core.components.ScrollPickerWheel
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel.WeightViewModel
import com.weappsinc.watertracker.app.feature.weight.presentation.viewmodel.WeightViewModelFactory

@Composable
fun WeightSelectionScreen(
    modifier: Modifier = Modifier,
    factory: WeightViewModelFactory,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val vm: WeightViewModel = viewModel(factory = factory)
    val selectedWeightKg by vm.weightKg.collectAsState()
    val weightValues = vm.displayRange().toList()

    Column(
        modifier = modifier.fillMaxSize().background(AppColors.GenderScreenBackground)
            .padding(horizontal = AppDimens.GenderScreenHorizontalPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            AppTopBar(onBack = onBack)
            Spacer(Modifier.height(AppDimens.AppBarTitleSpacing))
            Text(text = stringResource(R.string.weight_title), color = AppColors.GenderTitle, style = AppTypography.Title1)
            Spacer(Modifier.height(AppDimens.UnitToggleBottomSpacing))
        }
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            ScrollPickerWheel(
                values = weightValues,
                selectedValue = selectedWeightKg,
                onSelectedChange = vm::onSelectDisplayWeight,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        AppPrimaryButton(
            text = stringResource(R.string.next),
            onClick = { vm.saveSelection(onNext) },
            modifier = Modifier.padding(bottom = AppDimens.AgeButtonBottomPadding)
        )
    }
}
