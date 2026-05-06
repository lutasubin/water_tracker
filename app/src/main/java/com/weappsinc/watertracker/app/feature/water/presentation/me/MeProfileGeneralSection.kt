package com.weappsinc.watertracker.app.feature.water.presentation.me

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
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
        Column(
            modifier = Modifier
                .padding(top = AppDimens.MeProfileGeneralTopSpacing)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(AppDimens.MeProfileMenuCardSpacing),
        ) {
            MeGeneralMenuCard(
                label = stringResource(R.string.me_menu_language),
                icon = Icons.Outlined.Language,
                onClick = onLanguage,
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
}

@Composable
private fun MeGeneralMenuCard(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .background(AppColors.HomeCard)
            .clickable(onClick = onClick)
            .height(AppDimens.MeProfileMenuRowHeight)
            .padding(horizontal = AppDimens.HomeCardInnerPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AppColors.HomePrimary,
            modifier = Modifier.size(22.dp),
        )
        Text(
            text = label,
            style = AppTypography.BodyLarge,
            color = AppColors.HomeTitle,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = null,
            tint = AppColors.HomeMuted,
            modifier = Modifier.size(22.dp),
        )
    }
}
