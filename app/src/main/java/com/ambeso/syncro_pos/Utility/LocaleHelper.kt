package com.ambeso.syncro_pos.Utility

import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.core.os.ConfigurationCompat.getLocales
import java.util.*
import android.preference.PreferenceManager
import android.content.SharedPreferences







class LocaleHelper {



    @Suppress("DEPRECATION")
    fun setLocale(localeSpec: String){
        val locale: Locale
        if (localeSpec == "system") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = Resources.getSystem().getConfiguration().getLocales().get(0)
            } else {

                locale = Resources.getSystem().getConfiguration().locale
            }
        } else {
            locale = Locale(localeSpec)
        }
        Locale.setDefault(locale)
    }

}