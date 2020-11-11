package com.mobileedu02.smartschoolmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true)
    var quizId: Long = 0L,

    @ColumnInfo(name = "quiz_time")
    val quizTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "quiz_date")
    var quizDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "score")
    var score: Int = 0
)