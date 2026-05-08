package com.weappsinc.watertracker.app.feature.water.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.feature.water.presentation.me.MeProfileScreen
import com.weappsinc.watertracker.app.feature.water.presentation.me.rate.RateUsBottomSheet
import com.weappsinc.watertracker.app.feature.water.presentation.me.rate.RateUsViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.MeProfileViewModelFactory
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.screen.WeighTrackerScreen
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighTrackerViewModelFactory

@Composable
fun HomeScreen(
    waterTrackerFactory: WaterTrackerViewModelFactory,
    weighTrackerFactory: WeighTrackerViewModelFactory,
    meProfileFactory: MeProfileViewModelFactory,
    rateUsFactory: RateUsViewModelFactory,
    onEditWaterGoal: () -> Unit,
    onOpenReport: () -> Unit,
    onEditTall: () -> Unit,
    onEditWeight: () -> Unit,
    onOpenWeighGoalDetail: () -> Unit,
    onOpenLanguage: () -> Unit,
    onOpenPrivacyPolicy: () -> Unit,
    modifier: Modifier = Modifier
) {
    var tab by rememberSaveable { mutableStateOf(HomeTab.Water) }
    val context = LocalContext.current
    var showRateSheet by rememberSaveable { mutableStateOf(false) }
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.HomeBackground),
    ) {
        Column(Modifier.fillMaxSize()) {
            when (tab) {
                HomeTab.Water -> WaterTrackerScreen(
                    factory = waterTrackerFactory,
                    imageLoader = imageLoader,
                    onEditGoal = onEditWaterGoal,
                    onOpenReport = onOpenReport,
                    modifier = Modifier.weight(1f),
                )
                HomeTab.Bmi -> WeighTrackerScreen(
                    factory = weighTrackerFactory,
                    imageLoader = imageLoader,
                    onEditTall = onEditTall,
                    onEditWeight = onEditWeight,
                    onOpenWeighGoalDetail = onOpenWeighGoalDetail,
                    modifier = Modifier.weight(1f),
                )
                HomeTab.Me -> MeProfileScreen(
                    factory = meProfileFactory,
                    imageLoader = imageLoader,
                    onLanguage = onOpenLanguage,
                    onRateUs = { showRateSheet = true },
                    onPrivacyPolicy = onOpenPrivacyPolicy,
                    modifier = Modifier.weight(1f),
                )
            }
            HomeBottomBar(
                selected = tab,
                onSelect = { tab = it },
                imageLoader = imageLoader,
            )
        }
        if (showRateSheet) {
            RateUsBottomSheet(
                imageLoader = imageLoader,
                factory = rateUsFactory,
                onDismiss = { showRateSheet = false },
            )
        }
    }
}
