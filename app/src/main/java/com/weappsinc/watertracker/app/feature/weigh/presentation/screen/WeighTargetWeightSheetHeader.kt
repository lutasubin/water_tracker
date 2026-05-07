package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens

/** Tiêu đề sheet cân mục tiêu: icon + title + đóng. */
@Composable
internal fun WeighTargetWeightSheetHeader(
    imageLoader: ImageLoader,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = AssetPaths.GOAL_ICON,
            contentDescription = null,
            imageLoader = imageLoader,
            modifier = Modifier.size(WeighDimens.SheetHeaderIconSize),
            contentScale = ContentScale.Fit,
        )
        Text(
            stringResource(R.string.target_sheet_header_title),
            Modifier.weight(1f).padding(horizontal = 10.dp),
            style = AppTypography.Title3,
            color = AppColors.WeighGoalLabelMuted,
        )
        IconButton(onClick = onDismiss) {
            Icon(Icons.Filled.Close, stringResource(R.string.close), tint = AppColors.HomeMuted)
        }
    }
}
