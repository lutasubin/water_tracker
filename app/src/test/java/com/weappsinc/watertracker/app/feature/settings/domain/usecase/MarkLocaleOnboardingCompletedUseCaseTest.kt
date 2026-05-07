package com.weappsinc.watertracker.app.feature.settings.domain.usecase

import com.weappsinc.watertracker.app.feature.settings.domain.repository.LocalePreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class MarkLocaleOnboardingCompletedUseCaseTest {

    @Test
    fun invoke_callsRepository() = runBlocking {
        val repo = RecordingLocaleRepo()
        MarkLocaleOnboardingCompletedUseCase(repo).invoke()
        assertTrue(repo.markCompletedCalled)
    }

    private class RecordingLocaleRepo : LocalePreferencesRepository {
        var markCompletedCalled = false

        override fun observeLocaleOnboardingCompleted(): Flow<Boolean> = flowOf(false)

        override suspend fun markLocaleOnboardingCompleted() {
            markCompletedCalled = true
        }
    }
}
