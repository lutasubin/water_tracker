package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RateUsViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        RateUsViewModel() as T
}
