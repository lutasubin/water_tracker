package com.weappsinc.watertracker.app.core.ads

import com.weappsinc.watertracker.app.core.config.AdsConfig

data class AdsRuntimeState(
    val isEligible: Boolean = AdsEligibilityProvider.isAdsSupported(),
    val isInitialized: Boolean = false,
    val config: AdsConfig = AdsConfig.legacyDefaults(),
) {
    fun canRequestAds(): Boolean = isEligible && config.showAds
}
