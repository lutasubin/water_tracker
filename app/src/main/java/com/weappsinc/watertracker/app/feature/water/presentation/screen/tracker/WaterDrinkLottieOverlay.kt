package com.weappsinc.watertracker.app.feature.water.presentation.screen.tracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.weappsinc.watertracker.app.core.constants.AssetPaths

/**
 * Overlay toàn màn: chạy water_drink.json 1 lần rồi gọi [onFinished].
 * [playToken] đổi mỗi lần bấm DRINK — đảm bảo Lottie luôn chạy lại từ đầu.
 */
@Composable
fun WaterDrinkLottieOverlay(
    playToken: Int,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(AssetPaths.WATER_DRINK_LOTTIE)
    )
    val anim = rememberLottieAnimatable()
    LaunchedEffect(playToken, composition) {
        val c = composition ?: return@LaunchedEffect
        anim.snapTo(composition = c, progress = 0f, iteration = 1)
        anim.animate(composition = c, iterations = 1)
        onFinished()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f)),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = composition,
            progress = { anim.progress },
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .aspectRatio(1f),
        )
    }
}
