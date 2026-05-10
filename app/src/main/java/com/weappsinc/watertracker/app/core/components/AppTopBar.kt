package com.weappsinc.watertracker.app.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

// Top bar tự dựng để nút back nằm đúng cột nội dung — không bị Material TopAppBar pad mặc định.
@Composable
fun AppTopBar(
    title: String = "",
    onBack: () -> Unit,
    showBack: Boolean = true,
    containerColor: Color = AppColors.GenderScreenBackground,
    contentColor: Color = AppColors.GenderTitle,
    /** True khi screen tự đảm nhiệm padding ngang (vd Onboarding) → AppTopBar không thêm padding nữa. */
    matchParentHorizontalPadding: Boolean = true,
    /** Khi parent không có padding ngang, AppTopBar dùng giá trị fallback này để cân hai bên. */
    horizontalPaddingOverride: Dp = AppDimens.AppTopBarStandalonePadding,
    centerAligned: Boolean = false,
    titleStyle: TextStyle = AppTypography.BodyMedium,
    modifier: Modifier = Modifier,
) {
    val horizontalPadding = if (matchParentHorizontalPadding) 0.dp else horizontalPaddingOverride
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor)
            .height(AppDimens.AppTopBarHeight)
            .padding(horizontal = horizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        AppTopBarSideSlot(showContent = showBack) {
            AppTopBarBackIcon(onBack = onBack, contentColor = contentColor)
        }
        AppTopBarTitle(
            title = title,
            titleStyle = titleStyle,
            contentColor = contentColor,
            centerAligned = centerAligned,
        )
        if (centerAligned) AppTopBarSideSlot(showContent = false) {}
    }
}

@Composable
private fun AppTopBarSideSlot(
    showContent: Boolean,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.size(AppDimens.AppTopBarSideSlot),
        contentAlignment = Alignment.CenterStart,
    ) {
        if (showContent) content()
    }
}

@Composable
private fun AppTopBarBackIcon(onBack: () -> Unit, contentColor: Color) {
    // Icon align sát mép trái của hit area để trùng cột nội dung; touch target vẫn 40.dp.
    Box(
        modifier = Modifier
            .size(AppDimens.AppTopBarIconHitSize)
            .clip(CircleShape)
            .clickable(onClick = onBack),
        contentAlignment = Alignment.CenterStart,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.cd_back),
            tint = contentColor,
            modifier = Modifier.size(AppDimens.AppTopBarIconSize),
        )
    }
}

@Composable
private fun RowScope.AppTopBarTitle(
    title: String,
    titleStyle: TextStyle,
    contentColor: Color,
    centerAligned: Boolean,
) {
    if (title.isEmpty()) {
        Spacer(Modifier.weight(1f))
        return
    }
    Text(
        text = title,
        style = titleStyle,
        color = contentColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = if (centerAligned) TextAlign.Center else TextAlign.Start,
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = AppDimens.AppTopBarTitleSpacing),
    )
}
