package ru.konohovalex.feature.notes.data.source.storage.database.converter

import androidx.room.TypeConverter
import ru.konohovalex.core.utils.model.DateTime

class DateTimeConverter {
    @TypeConverter
    fun stringToDateTime(raw: String): DateTime = DateTime(raw)

    @TypeConverter
    fun dateTimeToString(dateTime: DateTime): String = dateTime.raw
}
