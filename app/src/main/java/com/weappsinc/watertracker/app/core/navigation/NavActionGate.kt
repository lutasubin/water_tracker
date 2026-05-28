package com.weappsinc.watertracker.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext

/** Chống nhấp đôi điều hướng: một thao tác xong + delay ngắn mới nhận thao tác kế tiếp. */
class NavActionGate(
    private val scope: CoroutineScope,
    private val settleMs: Long = 360L,
) {
    private val mutex = Mutex()

    fun run(block: () -> Unit) {
        scope.launch {
            if (!mutex.tryLock()) return@launch
            try {
                withContext(Dispatchers.Main.immediate) { block() }
                delay(settleMs)
            } finally {
                mutex.unlock()
            }
        }
    }

    /** Sau inter/app-open: điều hướng đồng bộ trên Main, không chờ settleMs. */
    fun runAfterFullscreenAd(block: () -> Unit) {
        if (!mutex.tryLock()) return
        try {
            block()
        } finally {
            mutex.unlock()
        }
    }
}

@Composable
fun rememberNavActionGate(): NavActionGate {
    val scope = rememberCoroutineScope()
    return remember(scope) { NavActionGate(scope) }
}
