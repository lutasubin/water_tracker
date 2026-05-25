package com.weappsinc.watertracker.app.core.ads

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf
import com.weappsinc.watertracker.app.core.config.AdsConfig
import kotlinx.coroutines.flow.StateFlow

interface AdsManager {
    val state: StateFlow<AdsRuntimeState>

    fun initialize(context: Context)

    suspend fun refreshConfig(): Result<AdsConfig>

    fun warmUp(context: Context)

    fun preloadAppOpen(context: Context)

    suspend fun awaitAppOpenReady(timeoutMs: Long): Boolean

    fun preloadInterstitial(context: Context)

    fun preloadRewarded(context: Context)

    fun showAppOpen(activity: Activity, onDismiss: () -> Unit = {})

    fun showInterstitial(activity: Activity, onDismiss: () -> Unit = {})

    fun showRewarded(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onDismiss: () -> Unit = {},
    )
}

val LocalAdsManager = staticCompositionLocalOf<AdsManager> {
    error("AdsManager chua duoc provide.")
}
