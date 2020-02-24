package com.ambeso.syncro_pos.Utility

import java.text.NumberFormat


class StringUtil {
    fun money(numberStr : String) :String{
        return NumberFormat.getNumberInstance().format(numberStr.toInt());
    }
}