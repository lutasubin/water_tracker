package com.weappsinc.watertracker.app.core.config

import kotlinx.coroutines.flow.StateFlow

interface RemoteConfigRepository {
    val adsConfig: StateFlow<AdsConfig>

    fun currentConfig(): AdsConfig = adsConfig.value

    suspend fun refresh(): Result<AdsConfig>
}
