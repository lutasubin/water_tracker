package com.weappsinc.watertracker.app.feature.weigh.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface WeighCompletedGoalDao {

    @Insert
    suspend fun insert(entity: WeighCompletedGoalEntity): Long

    @Query(
        """
        SELECT * FROM weigh_completed_goal
        ORDER BY completedAtMs DESC
        """
    )
    fun observeAllDesc(): Flow<List<WeighCompletedGoalEntity>>

    @Query(
        """
        SELECT * FROM weigh_completed_goal
        WHERE id = :goalId
        LIMIT 1
        """
    )
    fun observeById(goalId: Long): Flow<WeighCompletedGoalEntity?>

    @Insert
    suspend fun insertLogs(entities: List<WeighCompletedGoalLogEntity>)

    @Query(
        """
        SELECT * FROM weigh_completed_goal_log
        WHERE goalId = :goalId
        ORDER BY recordedAtMs ASC
        """
    )
    fun observeLogsByGoalId(goalId: Long): Flow<List<WeighCompletedGoalLogEntity>>

    @Transaction
    suspend fun insertGoalWithLogs(
        goal: WeighCompletedGoalEntity,
        logs: List<WeighCompletedGoalLogEntity>,
    ): Long {
        val goalId = insert(goal)
        if (logs.isNotEmpty()) {
            insertLogs(logs.map { it.copy(goalId = goalId) })
        }
        return goalId
    }
}
