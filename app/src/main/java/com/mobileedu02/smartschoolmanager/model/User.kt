package com.mobileedu02.smartschoolmanager.model

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Users(
    var surName: String? = "",
    var otherNames: String? = "",
    var phone: String? = "",
    var score: List<Score>? = emptyList(),
    var uuid: String? = ""
)