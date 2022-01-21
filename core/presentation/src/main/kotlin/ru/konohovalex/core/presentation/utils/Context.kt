package ru.konohovalex.core.presentation.utils

import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.Locale

/** In case to conveniently change the app locale, this method must be used in [android.app.Application.attachBaseContext] */
fun Context.changeLocale(locale: Locale): Context {
    val configuration = resources.configuration

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
    }
    else configuration.locale = locale
    configuration.setLayoutDirection(locale)

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
        createConfigurationContext(configuration)
    }
    else {
        resources.updateConfiguration(configuration, resources.displayMetrics)
        this
    }
}
