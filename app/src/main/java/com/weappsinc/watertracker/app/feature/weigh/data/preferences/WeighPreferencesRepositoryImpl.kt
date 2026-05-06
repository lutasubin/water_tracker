package com.weappsinc.watertracker.app.feature.weigh.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.weighDataStore: DataStore<Preferences> by preferencesDataStore(name = "weigh_prefs")

private object WeighKeys {
    val MASS_UNIT = stringPreferencesKey("mass_unit")
    val TARGET_KG = floatPreferencesKey("target_weight_kg")
    val JOURNEY_START_KG = floatPreferencesKey("journey_start_weight_kg")
}

/** DataStore `weigh_prefs`: đơn vị KG/LB và cân mục tiêu (kg), tách khỏi water prefs. */
class WeighPreferencesRepositoryImpl(
    context: Context
) : WeighPreferencesRepository {

    private val ds = context.applicationContext.weighDataStore

    override fun observeMassUnit(): Flow<MassUnit> =
        ds.data.map { prefs ->
            prefs[WeighKeys.MASS_UNIT]?.let { name ->
                runCatching { MassUnit.valueOf(name) }.getOrNull()
            } ?: MassUnit.KG
        }

    override suspend fun saveMassUnit(unit: MassUnit) {
        ds.edit { it[WeighKeys.MASS_UNIT] = unit.name }
    }

    override fun observeTargetWeightKg(): Flow<Float?> =
        ds.data.map { prefs ->
            if (!prefs.contains(WeighKeys.TARGET_KG)) null
            else prefs[WeighKeys.TARGET_KG]
        }

    override suspend fun saveTargetWeightKg(weightKg: Float?) {
        ds.edit { prefs ->
            if (weightKg == null) {
                prefs.remove(WeighKeys.TARGET_KG)
                prefs.remove(WeighKeys.JOURNEY_START_KG)
            } else prefs[WeighKeys.TARGET_KG] = weightKg
        }
    }

    override fun observeJourneyStartWeightKg(): Flow<Float?> =
        ds.data.map { prefs ->
            if (!prefs.contains(WeighKeys.JOURNEY_START_KG)) null
            else prefs[WeighKeys.JOURNEY_START_KG]
        }

    override suspend fun saveJourneyStartWeightKg(weightKg: Float?) {
        ds.edit { prefs ->
            if (weightKg == null) prefs.remove(WeighKeys.JOURNEY_START_KG)
            else prefs[WeighKeys.JOURNEY_START_KG] = weightKg
        }
    }
}
