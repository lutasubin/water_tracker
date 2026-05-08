package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Sheet đánh giá: chỉ quản state sao và signal đóng sau submit (chưa mở Store). */
class RateUsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RateUsUiState())
    val uiState: StateFlow<RateUsUiState> = _uiState.asStateFlow()

    private val _completedStars = MutableSharedFlow<Int>(extraBufferCapacity = 1)
    val completedStars: SharedFlow<Int> = _completedStars.asSharedFlow()

    fun selectStars(stars: Int) {
        _uiState.update { it.copy(selectedStars = stars.coerceIn(0, 5)) }
    }

    fun submit() {
        val n = _uiState.value.selectedStars
        if (n < 1) return
        viewModelScope.launch { _completedStars.emit(n) }
    }
}
