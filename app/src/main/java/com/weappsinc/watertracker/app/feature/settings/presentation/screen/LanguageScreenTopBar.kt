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
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.local.AppLocalePreferences
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** Thanh trên màn Language: quay lại, tiêu đề, xác nhận (lưu + áp locale). [applyContext] nên là applicationContext. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreenTopBar(
    selectedTag: String,
    scope: CoroutineScope,
    onBack: () -> Unit,
    applyContext: Context,
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
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back),
                    tint = AppColors.HomeTitle,
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    scope.launch {
                        AppLocalePreferences.saveTag(applyContext, selectedTag)
                        withContext(Dispatchers.Main) {
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(selectedTag)
                            )
                        }
                        onBack()
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
