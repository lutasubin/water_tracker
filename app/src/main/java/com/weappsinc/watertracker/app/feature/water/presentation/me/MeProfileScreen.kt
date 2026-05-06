package com.weappsinc.watertracker.app.feature.water.presentation.me

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.MeProfileViewModel
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.MeProfileViewModelFactory

/** Màn tab Me: Personal Data + General. */
@Composable
fun MeProfileScreen(
    factory: MeProfileViewModelFactory,
    imageLoader: ImageLoader,
    onLanguage: () -> Unit = {},
    onRateUs: () -> Unit = {},
    onShare: () -> Unit = {},
    onPrivacyPolicy: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val vm: MeProfileViewModel = viewModel(factory = factory)
    val state by vm.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = AppDimens.HomeHorizontalPadding),
    ) {
        Spacer(Modifier.padding(top = AppDimens.HomeSectionSpacing))
        Text(
            text = stringResource(R.string.me_personal_data_title),
            style = AppTypography.Title2,
            color = AppColors.HomeTitle,
        )
        MeProfileStatCardsRow(
            state = state,
            imageLoader = imageLoader,
            modifier = Modifier.padding(top = 16.dp),
        )
        MeProfileGeneralSection(
            onLanguage = onLanguage,
            onRateUs = onRateUs,
            onShare = onShare,
            onPrivacyPolicy = onPrivacyPolicy,
            modifier = Modifier.padding(top = AppDimens.MeProfileSectionTitleSpacing),
        )
        Spacer(Modifier.padding(bottom = 24.dp))
    }
}
