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
import com.weappsinc.watertracker.app.core.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String = "", onBack: () -> Unit, showBack: Boolean = true) {
    TopAppBar(
        title = { if (title.isNotEmpty()) Text(title, color = AppColors.GenderTitle) },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AppColors.GenderTitle)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.GenderScreenBackground)
    )
}
