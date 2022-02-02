package ru.konohovalex.core.presentation.extension

import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.Locale

fun Context.changeLocale(locale: Locale) {
    val configuration = resources.configuration

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
    }
    else configuration.locale = locale
    configuration.setLayoutDirection(locale)

    /** This method should've return Context to be used in [android.app.Application.attachBaseContext],
     * but there's no way to change locale dynamically with this approach, so ignore the deprecation
     * and just call [android.content.res.Resources.updateConfiguration] */
    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) createConfigurationContext(configuration)
    else */resources.updateConfiguration(configuration, resources.displayMetrics)
}
