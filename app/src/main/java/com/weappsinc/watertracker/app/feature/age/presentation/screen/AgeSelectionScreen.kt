package com.weappsinc.watertracker.app.feature.age.presentation.screen

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
import com.weappsinc.watertracker.app.feature.age.presentation.viewmodel.AgeViewModel
import com.weappsinc.watertracker.app.feature.age.presentation.viewmodel.AgeViewModelFactory

@Composable
fun AgeSelectionScreen(
    modifier: Modifier = Modifier,
    factory: AgeViewModelFactory,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val vm: AgeViewModel = viewModel(factory = factory)
    val selectedAge by vm.age.collectAsState()
    val ages = (10..80).toList()

    Column(
        modifier = modifier.fillMaxSize().background(AppColors.GenderScreenBackground)
            .padding(horizontal = AppDimens.GenderScreenHorizontalPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            AppTopBar(onBack = onBack)
            Spacer(Modifier.height(AppDimens.AppBarTitleSpacing))
            Text(
                text = stringResource(R.string.age_title),
                color = AppColors.GenderTitle,
                style = AppTypography.Title1
            )
            Spacer(Modifier.height(AppDimens.AgeTitleBottomSpacing))
        }
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            ScrollPickerWheel(
                values = ages,
                selectedValue = selectedAge,
                onSelectedChange = vm::onSelectAge,
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
