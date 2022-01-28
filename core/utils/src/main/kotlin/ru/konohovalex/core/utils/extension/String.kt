package ru.konohovalex.core.utils.extension

import java.lang.IndexOutOfBoundsException

/** Returns the substring of this string,
 * starting at the [startIndex] (inclusive) and ending right before the [endIndex] (exclusive).
 * If the original Java substring method throws IndexOutOfBoundsException,
 * returns [returnOnError], which defaults to the original string. */
fun String.safeSubstring(
    startIndex: Int,
    endIndex: Int,
    returnOnError: String = this,
): String = try {
    substring(startIndex, endIndex)
} catch (exception: IndexOutOfBoundsException) {
    returnOnError
}
