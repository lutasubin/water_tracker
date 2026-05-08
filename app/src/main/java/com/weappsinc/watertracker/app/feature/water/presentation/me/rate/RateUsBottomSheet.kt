package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.weappsinc.watertracker.app.core.theme.AppDimens

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

    val discardDraftAndDismiss: () -> Unit = {
        vm.resetDraft()
        onDismiss()
    }

    LaunchedEffect(Unit) {
        vm.resetDraft()
        vm.completedStars.collect {
            onDismiss()
        }
    }
    ModalBottomSheet(
        onDismissRequest = discardDraftAndDismiss,
        sheetState = sheetState,
        containerColor = AppColors.HomeCard,
        shape = RoundedCornerShape(
            topStart = AppDimens.RateUsSheetTopCorner,
            topEnd = AppDimens.RateUsSheetTopCorner,
        ),
        dragHandle = null,
    ) {
        RateUsSheetContent(
            selectedStars = state.selectedStars,
            imageLoader = imageLoader,
            onStarSelected = vm::selectStars,
            onSubmit = vm::submit,
            onClose = discardDraftAndDismiss,
        )
    }
}
