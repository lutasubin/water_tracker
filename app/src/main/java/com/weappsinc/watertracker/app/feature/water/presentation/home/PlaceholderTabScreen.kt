package com.weappsinc.watertracker.app.feature.water.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.R
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
            text = "${stringResource(R.string.tab_todo_placeholder)}: $title",
            color = AppColors.HomeMuted,
            style = AppTypography.BodyLarge
        )
    }
}
