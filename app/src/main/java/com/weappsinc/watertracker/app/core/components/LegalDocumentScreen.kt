package com.weappsinc.watertracker.app.core.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Khung chung cho màn hiển thị văn bản pháp lý (chính sách / điều khoản) trong app. */
@Composable
fun LegalDocumentScreen(
    @StringRes titleRes: Int,
    bodyText: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize().background(AppColors.HomeBackground)) {
        AppTopBar(
            title = stringResource(titleRes),
            onBack = onBack,
            containerColor = AppColors.HomeBackground,
            contentColor = AppColors.HomeTitle,
            centerAligned = true,
            titleStyle = AppTypography.Title3,
            matchParentHorizontalPadding = false,
        )
        Text(
            text = bodyText,
            style = AppTypography.BodyMedium,
            color = AppColors.HomeTitle,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = AppDimens.HomeHorizontalPadding,
                    vertical = AppDimens.HomeCardInnerPadding,
                ),
        )
    }
}
