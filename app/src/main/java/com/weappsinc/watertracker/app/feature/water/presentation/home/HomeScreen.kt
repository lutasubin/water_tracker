package com.weappsinc.watertracker.app.feature.water.presentation.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.weappsinc.watertracker.app.core.constants.LegalUrls
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.feature.water.presentation.screen.WaterTrackerScreen
import com.weappsinc.watertracker.app.feature.water.presentation.me.MeProfileScreen
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.MeProfileViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.screen.WeighTrackerScreen
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighTrackerViewModelFactory

@Composable
fun HomeScreen(
    waterTrackerFactory: WaterTrackerViewModelFactory,
    weighTrackerFactory: WeighTrackerViewModelFactory,
    meProfileFactory: MeProfileViewModelFactory,
    onEditWaterGoal: () -> Unit,
    onOpenReport: () -> Unit,
    onEditTall: () -> Unit,
    onEditWeight: () -> Unit,
    onOpenWeighGoalDetail: () -> Unit,
    onOpenLanguage: () -> Unit,
    modifier: Modifier = Modifier
) {
    var tab by rememberSaveable { mutableStateOf(HomeTab.Water) }
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.HomeBackground)
    ) {
        when (tab) {
            HomeTab.Water -> WaterTrackerScreen(
                factory = waterTrackerFactory,
                imageLoader = imageLoader,
                onEditGoal = onEditWaterGoal,
                onOpenReport = onOpenReport,
                modifier = Modifier.weight(1f)
            )
            HomeTab.Bmi -> WeighTrackerScreen(
                factory = weighTrackerFactory,
                imageLoader = imageLoader,
                onEditTall = onEditTall,
                onEditWeight = onEditWeight,
                onOpenWeighGoalDetail = onOpenWeighGoalDetail,
                modifier = Modifier.weight(1f)
            )
            HomeTab.Me -> MeProfileScreen(
                factory = meProfileFactory,
                imageLoader = imageLoader,
                onLanguage = onOpenLanguage,
                onPrivacyPolicy = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(LegalUrls.PRIVACY_POLICY),
                    )
                    context.startActivity(Intent.createChooser(intent, null))
                },
                modifier = Modifier.weight(1f)
            )
        }
        HomeBottomBar(
            selected = tab,
            onSelect = { tab = it },
            imageLoader = imageLoader
        )
    }
}
