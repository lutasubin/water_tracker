package com.weappsinc.watertracker.app.feature.water.presentation.me.rate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RateUsViewModelTest {

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun submit_khiChuaChonSao_khongPhatCompleted() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val vm = RateUsViewModel()
        var received: Int? = null
        val job = launch {
            vm.completedStars.collect { received = it }
        }
        vm.submit()
        assertNull(received)
        job.cancel()
    }

    @Test
    fun submit_khiCoSao_phátĐúngGiáTrị() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val vm = RateUsViewModel()
        vm.selectStars(4)
        val emissions = mutableListOf<Int>()
        val job = launch {
            vm.completedStars.collect { emissions.add(it) }
        }
        vm.submit()
        assertEquals(listOf(4), emissions)
        assertEquals(0, vm.uiState.value.selectedStars)
        job.cancel()
    }

    @Test
    fun resetDraft_xoaChonSao() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val vm = RateUsViewModel()
        vm.selectStars(3)
        vm.resetDraft()
        assertEquals(0, vm.uiState.value.selectedStars)
    }
}
