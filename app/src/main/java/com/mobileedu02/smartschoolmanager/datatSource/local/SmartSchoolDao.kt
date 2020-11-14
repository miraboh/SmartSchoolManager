package com.mobileedu02.smartschoolmanager.datatSource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mobileedu02.smartschoolmanager.model.History


@Dao
interface SmartSchoolDao {

    @Insert
    suspend fun insert(history: History)

    @Query("DELETE FROM history_table")
    suspend fun clear()

    @Query("SELECT * FROM history_table ORDER BY quizId DESC")
    fun getAllHistory(): LiveData<List<History>>

}