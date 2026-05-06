package com.weappsinc.watertracker.app.core.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
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
    /** Material 3: Top app bar căn giữa tiêu đề (màn chi tiết / settings). */
    centerAligned: Boolean = false,
    titleStyle: TextStyle = AppTypography.BodyMedium,
    modifier: Modifier = Modifier
) {
    val colors = TopAppBarDefaults.topAppBarColors(
        containerColor = containerColor,
        titleContentColor = contentColor,
        navigationIconContentColor = contentColor
    )
    val centerColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = containerColor,
        titleContentColor = contentColor,
        navigationIconContentColor = contentColor
    )
    val titleComposable: @Composable () -> Unit = {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = titleStyle,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    val navIcon: @Composable () -> Unit = {
        if (showBack) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = contentColor
                )
            }
        }
    }
    if (centerAligned) {
        CenterAlignedTopAppBar(
            title = titleComposable,
            navigationIcon = navIcon,
            colors = centerColors,
            windowInsets = TopAppBarDefaults.windowInsets,
            modifier = modifier.fillMaxWidth()
        )
    } else {
        TopAppBar(
            title = titleComposable,
            navigationIcon = navIcon,
            colors = colors,
            windowInsets = TopAppBarDefaults.windowInsets,
            modifier = modifier.fillMaxWidth()
        )
    }
}
