package com.weappsinc.watertracker.app.core.ads

enum class AdUiLoadState {
    Loading,
    Loaded,
    Failed,
}

const val ADS_RETRY_DELAY_MS = 3_000L
