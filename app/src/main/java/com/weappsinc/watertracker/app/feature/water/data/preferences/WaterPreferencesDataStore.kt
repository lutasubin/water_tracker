package com.weappsinc.watertracker.app.feature.water.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId

private val Context.waterPrefsDataStore: DataStore<Preferences> by preferencesDataStore(name = "water_prefs")

private object Keys {
    val FIRST_INSTALL_EPOCH_DAY = longPreferencesKey("first_install_epoch_day")
    val SAVED_GOAL_ML = intPreferencesKey("saved_goal_ml")
    val WATER_UNIT = stringPreferencesKey("water_unit")
}

class WaterPreferencesRepositoryImpl(
    context: Context
) : WaterPreferencesRepository {

    private val ds = context.applicationContext.waterPrefsDataStore

    override suspend fun ensureFirstInstallEpochDayRecorded() {
        ds.edit { prefs ->
            if (!prefs.contains(Keys.FIRST_INSTALL_EPOCH_DAY)) {
                val zone = ZoneId.systemDefault()
                val today = LocalDate.now(zone).toEpochDay()
                prefs[Keys.FIRST_INSTALL_EPOCH_DAY] = today
            }
        }
    }

    override fun observeFirstInstallEpochDay(): Flow<Long?> =
        ds.data.map { it[Keys.FIRST_INSTALL_EPOCH_DAY] }

    override suspend fun saveGoalMlAndUnit(goalMl: Int, unit: WaterUnit) {
        ds.edit { prefs ->
            prefs[Keys.SAVED_GOAL_ML] = goalMl
            prefs[Keys.WATER_UNIT] = unit.name
        }
    }

    override fun observeSavedGoalMl(): Flow<Int?> =
        ds.data.map { it[Keys.SAVED_GOAL_ML] }

    override fun observeSavedUnit(): Flow<WaterUnit?> =
        ds.data.map { prefs ->
            prefs[Keys.WATER_UNIT]?.let { name ->
                runCatching { WaterUnit.valueOf(name) }.getOrNull()
            }
        }
}
