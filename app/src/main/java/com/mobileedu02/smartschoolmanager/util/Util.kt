package com.mobileedu02.smartschoolmanager.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.mobileedu02.smartschoolmanager.R
import com.mobileedu02.smartschoolmanager.model.History
import java.text.SimpleDateFormat


fun View.hideKeyboard(view: View){
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
}

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}
