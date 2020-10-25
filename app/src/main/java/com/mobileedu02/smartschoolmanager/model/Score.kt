package com.mobileedu02.smartschoolmanager.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Score(
    var score: Int? = 0)
