package com.weappsinc.watertracker.app.feature.water.presentation.me

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppDimens

/** Khối Hỗ trợ: ngôn ngữ, đánh giá, chia sẻ, quyền riêng tư. */
@Composable
fun MeProfileSupportMenuRows(
    onLanguage: () -> Unit,
    onRateUs: () -> Unit,
    onShare: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    languageValueText: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppDimens.MeProfileMenuCardSpacing),
    ) {
        MeGeneralMenuCard(
            label = stringResource(R.string.me_menu_language),
            icon = Icons.Outlined.Language,
            onClick = onLanguage,
            valueText = languageValueText,
        )
        MeGeneralMenuCard(
            label = stringResource(R.string.me_menu_rate_us),
            icon = Icons.Outlined.Star,
            onClick = onRateUs,
        )
        MeGeneralMenuCard(
            label = stringResource(R.string.me_menu_share),
            icon = Icons.Outlined.Share,
            onClick = onShare,
        )
        MeGeneralMenuCard(
            label = stringResource(R.string.me_menu_privacy),
            icon = Icons.Outlined.VerifiedUser,
            onClick = onPrivacyPolicy,
        )
    }
}
