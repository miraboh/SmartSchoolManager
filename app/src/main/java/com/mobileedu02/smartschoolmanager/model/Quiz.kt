package com.mobileedu02.smartschoolmanager.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quiz(
    var unId: Int? = null,
    var rightAns: String? = null,
    var question: String? = null,
    var A: String? = null,
    var B: String? = null,
    var C: String? = null,
    var D: String? = null
):Parcelable
