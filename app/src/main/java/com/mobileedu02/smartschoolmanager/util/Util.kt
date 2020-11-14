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
import java.util.Objects.toString


fun View.hideKeyboard(view: View){
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
}

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}
@SuppressLint("toString")
fun convertscoreToString(score: Int): String {
    return toString(":")
        .format(score)
}

fun formatHistory(history: List<History>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        history.forEach {
            //append("\t ${it.score}:")

            append("<br>")
            append("\t${convertLongToDateString(it.quizTime)}<br>")
            // Hours
            //append("\t ${it.quizTime.minus(it.quizTime) / 1000 / 60 / 60}:")
            // Minutes
            //append("${it.quizTime.minus(it.quizTime) / 1000 / 60}:")
            // Seconds
            //append("${it.quizTime.minus(it.quizTime) / 1000}<br><br>")
            append("\t${resources.getString(R.string.score)}")
            append("\t${it.score}<br>")

        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}