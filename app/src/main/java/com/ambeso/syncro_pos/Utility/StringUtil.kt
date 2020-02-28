package com.ambeso.syncro_pos.Utility

import java.text.DateFormat
import java.text.NumberFormat
import java.util.*
import android.widget.Toast
import android.R
import java.text.ParseException
import java.text.SimpleDateFormat


class StringUtil {
    fun money(numberStr : String) :String{
        return NumberFormat.getNumberInstance().format(numberStr.toInt());
    }

    fun currentTime(): String?{
        var dateFormatted : String?
        val currentTime = Calendar.getInstance().getTime()
        LocaleHelper().setLocale("in");
        try {
//            val newFormat = SimpleDateFormat("EEEE, dd MMMM yyyy h:mm a", Locale.getDefault())
            val newFormat = SimpleDateFormat("dd MMMM yyyy H:mm", Locale.getDefault())
            dateFormatted = newFormat.format(currentTime)
        } catch (e: ParseException) {
            e.printStackTrace()
            dateFormatted = DateFormat.getDateTimeInstance().format(currentTime)

        }

        return dateFormatted
    }
}