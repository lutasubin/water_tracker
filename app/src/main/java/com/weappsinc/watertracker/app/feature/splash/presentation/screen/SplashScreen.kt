package com.weappsinc.watertracker.app.feature.splash.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import coil.compose.AsyncImage
import coil.ImageLoader
import coil.decode.SvgDecoder
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onSplashFinished: () -> Unit = {}
) {
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }
    val infiniteTransition = rememberInfiniteTransition(label = "splash_progress")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "splash_progress_value"
    )
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.SplashBackgroundSolid)
    ) {
        AsyncImage(
            model = AssetPaths.SPLASH_BACKGROUND,
            contentDescription = AppText.SPLASH_BACKGROUND_DESC,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppDimens.SplashHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = AssetPaths.SPLASH_ICON,
                contentDescription = AppText.SPLASH_ICON_DESC,
                imageLoader = imageLoader,
                modifier = Modifier
                    .fillMaxWidth(AppDimens.SplashIconWidthFraction)
            )

            Spacer(modifier = Modifier.height(AppDimens.SplashTitleTopSpacing))

            Text(
                text = AppText.SPLASH_TITLE,
                color = AppColors.SplashTitle,
                style = AppTypography.Title2
            )
        }

        LinearProgressIndicator(
            progress = { progress },
            color = AppColors.SplashProgress,
            trackColor = AppColors.SplashProgressTrack,
            drawStopIndicator = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    start = AppDimens.SplashProgressHorizontalPadding,
                    end = AppDimens.SplashProgressHorizontalPadding,
                    bottom = AppDimens.SplashProgressBottomPadding
                )
                .fillMaxWidth()
                .height(AppDimens.SplashProgressHeight)
                .clip(RoundedCornerShape(AppDimens.SplashProgressCorner))
        )
    }
}
