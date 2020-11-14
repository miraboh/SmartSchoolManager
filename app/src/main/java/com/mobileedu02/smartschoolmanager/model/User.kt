package com.mobileedu02.smartschoolmanager.model

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class User(
    var surName: String? = "",
    var otherNames: String? = "",
    var phone: String? = "",
    var score: Int? = 0
)