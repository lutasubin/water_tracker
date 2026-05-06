package com.weappsinc.watertracker.app.feature.exercise.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.components.AppPrimaryButton
import com.weappsinc.watertracker.app.core.components.AppTopBar
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.exercise.domain.model.ExerciseLevel
import com.weappsinc.watertracker.app.feature.exercise.presentation.viewmodel.ExerciseSelectionViewModel
import com.weappsinc.watertracker.app.feature.exercise.presentation.viewmodel.ExerciseSelectionViewModelFactory

@Composable
fun ExerciseSelectionScreen(
    modifier: Modifier = Modifier,
    factory: ExerciseSelectionViewModelFactory,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val vm: ExerciseSelectionViewModel = viewModel(factory = factory)
    val selected by vm.selectedLevel.collectAsState()
    val context = LocalContext.current
    val imageLoader = remember { ImageLoader.Builder(context).components { add(SvgDecoder.Factory()) }.build() }
    Column(
        modifier = modifier.fillMaxSize().background(AppColors.GenderScreenBackground)
            .padding(horizontal = AppDimens.GenderScreenHorizontalPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            AppTopBar(onBack = onBack)
            Spacer(Modifier.height(AppDimens.AppBarTitleSpacing))
            Text(text = stringResource(R.string.exercise_title), color = AppColors.GenderTitle, style = AppTypography.Title1)
            Spacer(Modifier.height(AppDimens.UnitToggleBottomSpacing))
            ExerciseCard(stringResource(R.string.exercise_low), AssetPaths.EXERCISE_LOW_ICON, selected == ExerciseLevel.LOW, imageLoader) { vm.onSelectLevel(ExerciseLevel.LOW) }
            Spacer(Modifier.height(AppDimens.GenderOptionSpacing))
            ExerciseCard(stringResource(R.string.exercise_moderate), AssetPaths.EXERCISE_MODERATE_ICON, selected == ExerciseLevel.MODERATE, imageLoader) { vm.onSelectLevel(ExerciseLevel.MODERATE) }
            Spacer(Modifier.height(AppDimens.GenderOptionSpacing))
            ExerciseCard(stringResource(R.string.exercise_high), AssetPaths.EXERCISE_HIGH_ICON, selected == ExerciseLevel.HIGH, imageLoader) { vm.onSelectLevel(ExerciseLevel.HIGH) }
        }
        AppPrimaryButton(
            text = stringResource(R.string.next),
            onClick = { vm.saveSelection(); onNext() },
            modifier = Modifier.padding(bottom = AppDimens.AgeButtonBottomPadding)
        )
    }
}

@Composable
private fun ExerciseCard(title: String, iconPath: String, selected: Boolean, imageLoader: ImageLoader, onClick: () -> Unit) {
    val bg = if (selected) AppColors.GenderPrimary else AppColors.GenderUnselectedBackground
    val fg = if (selected) AppColors.GenderSelectedContent else AppColors.GenderUnselectedContent
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(AppDimens.GenderOptionCorner),
        colors = ButtonDefaults.buttonColors(containerColor = bg),
        modifier = Modifier.fillMaxWidth().height(AppDimens.GenderOptionHeight)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = iconPath,
                contentDescription = title,
                imageLoader = imageLoader,
                modifier = Modifier.size(AppDimens.GenderOptionIconSize)
            )
            Spacer(Modifier.size(12.dp))
            Text(text = title, color = fg, style = AppTypography.BodyLarge)
            Spacer(Modifier.weight(1f))
            if (selected) Icon(Icons.Default.CheckCircle, "Selected", tint = AppColors.GenderSelectedContent)
        }
    }
}
