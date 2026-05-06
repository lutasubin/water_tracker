package com.weappsinc.watertracker.app.feature.water.presentation.me

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker.StreakFireLottie
import com.weappsinc.watertracker.app.feature.water.presentation.state.MeProfileUiState

/** Hai thẻ Total Drinking / Streak (SVG drink + Lottie Fire streak). */
@Composable
fun MeProfileStatCardsRow(
    state: MeProfileUiState,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
) {
    val totalText =
        "${state.totalDrinkingMl} ${stringResource(R.string.unit_ml)}"
    val streakText = pluralStringResource(
        R.plurals.me_streak_days,
        state.streakDays,
        state.streakDays,
    )
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        MeProfileStatCard(
            primaryText = totalText,
            caption = stringResource(R.string.me_total_drinking_label),
            modifier = Modifier.weight(1f),
        ) {
            AsyncImage(
                model = AssetPaths.DRINK_ASSET,
                contentDescription = stringResource(R.string.me_total_drinking_label),
                imageLoader = imageLoader,
                modifier = Modifier.size(AppDimens.MeProfileStatIconSize),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(AppColors.HomePrimary),
            )
        }
        MeProfileStatCard(
            primaryText = streakText,
            caption = stringResource(R.string.me_streak_section_label),
            modifier = Modifier.weight(1f),
        ) {
            StreakFireLottie(Modifier.size(AppDimens.MeProfileStatIconSize))
        }
    }
}

@Composable
private fun MeProfileStatCard(
    primaryText: String,
    caption: String,
    modifier: Modifier = Modifier,
    leading: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(AppDimens.HomeCardCorner))
            .background(AppColors.HomeCard)
            .padding(AppDimens.HomeCardInnerPadding),
    ) {
        leading()
        Text(
            text = primaryText,
            style = AppTypography.BodyLarge,
            color = AppColors.HomeTitle,
            modifier = Modifier.padding(top = 10.dp),
        )
        Text(
            text = caption,
            style = AppTypography.BodyMedium,
            color = AppColors.HomeMuted,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}
