package com.weappsinc.watertracker.app.feature.water.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.components.LegalDocumentScreen
import com.weappsinc.watertracker.app.core.constants.LegalAssetPaths

/** Chính sách riêng tư — nội dung đọc từ assets (không mở URL). */
@Composable
fun PrivacyPolicyScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val body = remember {
        context.assets.open(LegalAssetPaths.PRIVACY_POLICY).bufferedReader().use { it.readText() }
    }
    LegalDocumentScreen(
        titleRes = R.string.me_menu_privacy,
        bodyText = body,
        onBack = onBack,
        modifier = modifier,
    )
}
