package com.weappsinc.watertracker.app.core.ads

import android.os.Build

object AdsEligibilityProvider {
    fun isAdsSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
}
