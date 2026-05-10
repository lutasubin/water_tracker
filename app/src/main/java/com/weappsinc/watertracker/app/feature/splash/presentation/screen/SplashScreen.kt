package com.weappsinc.watertracker.app.feature.splash.presentation.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.components.CapsuleProgressBar
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onBootstrap: suspend () -> Unit = {},
    onSplashFinished: () -> Unit = {},
) {
    LaunchedEffect(Unit) {
        onBootstrap()
        delay(2000)
        onSplashFinished()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.SplashBackgroundSolid),
    ) {
        AsyncImage(
            model = AssetPaths.SPLASH_BACKGROUND,
            contentDescription = stringResource(R.string.splash_background_desc),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        SplashContent()
        SplashProgressBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    start = AppDimens.SplashProgressHorizontalPadding,
                    end = AppDimens.SplashProgressHorizontalPadding,
                    bottom = AppDimens.SplashProgressBottomPadding,
                ),
        )
    }
}

@Composable
private fun SplashContent() {
    val context = LocalContext.current
    // PNG dùng decoder mặc định Coil; crossfade(false) → không nhấp nháy.
    val iconPainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(AssetPaths.SPLASH_ICON)
            .crossfade(false)
            .build(),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppDimens.SplashHorizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = iconPainter,
            contentDescription = stringResource(R.string.splash_icon_desc),
            modifier = Modifier.fillMaxWidth(AppDimens.SplashIconWidthFraction),
        )
        Spacer(modifier = Modifier.height(AppDimens.SplashTitleTopSpacing))
        Text(
            text = stringResource(R.string.splash_title),
            color = AppColors.SplashTitle,
            style = AppTypography.Title2,
        )
    }
}

@Composable
private fun SplashProgressBar(modifier: Modifier = Modifier) {
    // Tách riêng để chỉ progress bar recompose mỗi frame, không kéo theo icon.
    val infiniteTransition = rememberInfiniteTransition(label = "splash_progress")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "splash_progress_value",
    )
    CapsuleProgressBar(
        progressFraction = progress,
        modifier = modifier,
        height = AppDimens.SplashProgressHeight,
        trackColor = AppColors.SplashProgressTrack,
        fillColor = AppColors.SplashProgress,
    )
}
