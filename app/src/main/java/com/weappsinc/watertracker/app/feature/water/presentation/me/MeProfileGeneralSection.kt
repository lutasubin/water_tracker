package com.weappsinc.watertracker.app.feature.water.presentation.me

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Khối General: mỗi dòng một thẻ trắng riêng, có khoảng cách (không liền một khối). */
@Composable
fun MeProfileGeneralSection(
    onLanguage: () -> Unit,
    onRateUs: () -> Unit,
    onShare: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.me_general_section),
            style = AppTypography.Title3,
            color = AppColors.HomeTitle,
        )
        MeProfileGeneralMenuRows(
            onLanguage = onLanguage,
            onRateUs = onRateUs,
            onShare = onShare,
            onPrivacyPolicy = onPrivacyPolicy,
            modifier = Modifier
                .padding(top = AppDimens.MeProfileGeneralTopSpacing)
                .fillMaxWidth(),
        )
    }
}
