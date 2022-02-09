package ru.konohovalex.core.utils.model

import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import java.util.Locale

data class DateTime(val raw: String = SimpleDateFormat(FORMAT, Locale.getDefault()).format(GregorianCalendar.getInstance().time)) {
    companion object {
        const val FORMAT = "dd-MM-yyyy HH:mm"
    }

    // tbd parse raw + add today/yesterday
    fun getDateTimeString(): String = raw
}
