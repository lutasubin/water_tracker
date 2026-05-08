package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import com.weappsinc.watertracker.app.core.theme.AppColors

/** Bottom sheet Rate Us: trượt từ đáy màn hình. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateUsBottomSheet(
    imageLoader: ImageLoader,
    factory: RateUsViewModelFactory,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val vm: RateUsViewModel = viewModel(factory = factory)
    val state by vm.uiState.collectAsState()

    LaunchedEffect(Unit) {
        vm.completedStars.collect {
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppColors.HomeCard,
    ) {
        RateUsSheetContent(
            selectedStars = state.selectedStars,
            imageLoader = imageLoader,
            onStarSelected = vm::selectStars,
            onSubmit = vm::submit,
        )
    }
}
