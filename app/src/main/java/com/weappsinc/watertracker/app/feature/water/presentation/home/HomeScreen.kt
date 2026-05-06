package com.weappsinc.watertracker.app.feature.water.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.remember
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.feature.water.presentation.screen.WaterTrackerScreen
import com.weappsinc.watertracker.app.feature.water.presentation.viewmodel.WaterTrackerViewModelFactory
import com.weappsinc.watertracker.app.feature.weigh.presentation.screen.WeighTrackerScreen
import com.weappsinc.watertracker.app.feature.weigh.presentation.viewmodel.WeighTrackerViewModelFactory

@Composable
fun HomeScreen(
    waterTrackerFactory: WaterTrackerViewModelFactory,
    weighTrackerFactory: WeighTrackerViewModelFactory,
    onEditWaterGoal: () -> Unit,
    onOpenReport: () -> Unit,
    onEditTall: () -> Unit,
    onEditWeight: () -> Unit,
    onOpenWeighGoalDetail: () -> Unit,
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
            HomeTab.Me -> PlaceholderTabScreen(
                title = AppText.HOME_TAB_ME,
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
