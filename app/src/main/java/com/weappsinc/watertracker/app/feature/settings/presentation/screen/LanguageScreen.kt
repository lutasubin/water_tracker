package com.weappsinc.watertracker.app.feature.settings.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.weappsinc.watertracker.app.core.constants.AppLanguageCatalog
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.local.AppLocalePreferences
import com.weappsinc.watertracker.app.core.theme.AppColors

/** Màn chọn ngôn ngữ: lưu DataStore + áp dụng AppCompat per-app locale. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val imageLoader = remember {
        ImageLoader.Builder(context).components { add(SvgDecoder.Factory()) }.build()
    }
    var selectedTag by remember { mutableStateOf(AppLocalePreferences.DEFAULT_LOCALE_TAG) }
    LaunchedEffect(Unit) {
        selectedTag = AppLocalePreferences.readTag(context)
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppColors.HomeBackground,
        topBar = {
            LanguageScreenTopBar(
                selectedTag = selectedTag,
                scope = scope,
                onBack = onBack,
                applyContext = context,
            )
        },
    ) { inner ->
        LazyColumn(
            Modifier
                .padding(inner)
                .padding(horizontal = 16.dp)
                .background(AppColors.HomeBackground),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp),
        ) {
            items(AppLanguageCatalog.options, key = { it.localeTag }) { opt ->
                LanguageOptionRow(
                    label = stringResource(opt.labelRes),
                    flagUrl = AssetPaths.flagAsset(opt.flagAssetFile),
                    imageLoader = imageLoader,
                    selected = opt.localeTag == selectedTag,
                    onSelect = { selectedTag = opt.localeTag },
                )
            }
        }
    }
}
