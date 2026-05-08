package com.weappsinc.watertracker.app.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.weappsinc.watertracker.app.core.constants.AssetPaths

/** Pháo hoa Lottie đạt mục tiêu (tab Water): không scrim, chồng trực tiếp lên nền app. Chạy 1 lần rồi [onFinished]. */
@Composable
fun FireworksCelebrationOverlay(
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(AssetPaths.FIREWORKS_LOTTIE)
    )
    val animationState = animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        isPlaying = composition != null,
    )
    var finished by remember { mutableStateOf(false) }
    LaunchedEffect(composition, animationState.isAtEnd) {
        if (composition == null || finished) return@LaunchedEffect
        if (animationState.isAtEnd) {
            finished = true
            onFinished()
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = composition,
            progress = { animationState.progress },
            modifier = Modifier.fillMaxSize(),
        )
    }
}
