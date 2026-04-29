package com.weappsinc.watertracker.app.core.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String = "",
    onBack: () -> Unit,
    showBack: Boolean = true,
    containerColor: Color = AppColors.GenderScreenBackground,
    contentColor: Color = AppColors.GenderTitle,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            if (title.isNotEmpty()) {
                Text(text = title, color = contentColor, style = AppTypography.BodyMedium)
            }
        },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = contentColor)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = containerColor),
        windowInsets = TopAppBarDefaults.windowInsets,
        modifier = modifier.fillMaxWidth()
    )
}
