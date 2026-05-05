package com.weappsinc.watertracker.app.feature.water.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography

@Composable
fun PlaceholderTabScreen(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.HomeBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${AppText.TAB_TODO_PLACEHOLDER}: $title",
            color = AppColors.HomeMuted,
            style = AppTypography.BodyLarge
        )
    }
}
