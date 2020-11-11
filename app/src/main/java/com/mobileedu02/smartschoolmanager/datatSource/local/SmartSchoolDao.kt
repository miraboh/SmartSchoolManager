package com.mobileedu02.smartschoolmanager.datatSource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mobileedu02.smartschoolmanager.model.History


@Dao
interface SmartSchoolDao {

    @Insert
    suspend fun insert(history: History)

    @Update
    suspend fun update(history: History)

    @Query("SELECT * from history_table WHERE quizId = :key")
    suspend fun get(key: Long): History?

    @Query("DELETE FROM history_table")
    suspend fun clear()

    @Query("SELECT * FROM history_table ORDER BY quizId DESC")
    fun getAllHistory(): LiveData<List<History>>

}


