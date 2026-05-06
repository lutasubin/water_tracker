package com.weappsinc.watertracker.app.feature.settings.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.ImageLoader
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Một dòng ngôn ngữ: cờ + tên + radio (mockup danh sách). */
@Composable
fun LanguageOptionRow(
    label: String,
    flagUrl: String,
    imageLoader: ImageLoader,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(AppDimens.HomeCardCorner)
    val border = if (selected) Modifier.border(2.dp, AppColors.HomePrimary, shape) else Modifier
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .then(border)
            .background(AppColors.HomeCard)
            .clickable(onClick = onSelect)
            .padding(AppDimens.HomeCardInnerPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = flagUrl,
            contentDescription = null,
            imageLoader = imageLoader,
            modifier = Modifier.size(36.dp),
            contentScale = ContentScale.Fit,
        )
        Spacer(Modifier.size(12.dp))
        Text(
            text = label,
            style = AppTypography.BodyLarge,
            color = AppColors.HomeTitle,
            modifier = Modifier.weight(1f),
        )
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = AppColors.HomePrimary,
                unselectedColor = AppColors.HomeMuted,
            ),
        )
    }
}
