package com.weappsinc.watertracker.app.feature.settings.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import android.content.Context
import java.util.Locale
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.local.AppLocalePreferences
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext

/** Top bar chọn ngôn ngữ: ✓ → lưu → điều hướng → chỉ đổi locale nếu khác app hiện tại (tránh nháy đen). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreenTopBar(
    selectedTag: String,
    scope: CoroutineScope,
    showBackButton: Boolean,
    applyContext: Context,
    /** Gọi trước khi áp locale trên main (vd. đánh dấu onboarding xong). */
    beforeApplyMain: suspend () -> Unit,
    /** Tránh hai lần ✓ chồng hai coroutine đều gọi onApplied/pop. */
    applyMutex: Mutex,
    /** Sau khi lưu tag + áp locale (Main). */
    onApplied: () -> Unit,
    onBack: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(R.string.language_screen_title),
                style = AppTypography.Title2,
                color = AppColors.HomeTitle,
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cd_back),
                        tint = AppColors.HomeTitle,
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = {
                    scope.launch {
                        if (!applyMutex.tryLock()) return@launch
                        try {
                            AppLocalePreferences.saveTag(applyContext, selectedTag)
                            beforeApplyMain()
                            // Điều hướng trước; chỉ gọi setLocales khi khác app hiện tại (tránh một nháy đen không cần).
                            withContext(Dispatchers.Main.immediate) { onApplied() }
                            delay(120L)
                            withContext(Dispatchers.Main.immediate) {
                                val cur = AppCompatDelegate.getApplicationLocales()
                                if (!applicationLocalesCompatibleWithSelected(cur, selectedTag)) {
                                    AppCompatDelegate.setApplicationLocales(
                                        LocaleListCompat.forLanguageTags(selectedTag)
                                    )
                                }
                            }
                        } finally {
                            applyMutex.unlock()
                        }
                    }
                }
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(R.string.cd_language_apply),
                    tint = AppColors.HomePrimary,
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = AppColors.HomeBackground,
            titleContentColor = AppColors.HomeTitle,
            navigationIconContentColor = AppColors.HomeTitle,
            actionIconContentColor = AppColors.HomeTitle,
        ),
    )
}

/** Khớp ngôn ngữ (+ vùng nếu cả hai khai báo); "vi" chấp nhận máy vi-VN → không cần đổi locale. */
private fun applicationLocalesCompatibleWithSelected(
    applicationLocales: LocaleListCompat,
    selectedTag: String,
): Boolean {
    val desired = Locale.forLanguageTag(selectedTag)
    if (desired.language.isEmpty()) return false
    repeat(applicationLocales.size()) { i ->
        val cur = applicationLocales[i] ?: return@repeat
        if (!desired.language.equals(cur.language, ignoreCase = true)) return@repeat
        val d = desired.country.takeIf { it.isNotBlank() } ?: ""
        val c = cur.country.takeIf { it.isNotBlank() } ?: ""
        if (d.isEmpty() || c.isEmpty()) return true
        if (c.equals(d, ignoreCase = true)) return true
    }
    return false
}
