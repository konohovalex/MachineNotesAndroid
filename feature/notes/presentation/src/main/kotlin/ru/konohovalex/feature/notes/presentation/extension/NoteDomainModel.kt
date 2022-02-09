package ru.konohovalex.feature.notes.presentation.extension

import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.core.utils.extension.firstOfClassOrNull
import ru.konohovalex.core.utils.extension.safeSubstring
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.presentation.R

internal fun NoteDomainModel.getTitleTextWrapper(emptyIfAbsent: Boolean = false): TextWrapper = noteContent
    .firstOfClassOrNull<NoteContentDomainModel.Text>()
    ?.content
    ?.substringBefore(delimiter = "\n")
    ?.trimStart { it == '\n' || it.isWhitespace() }
    ?.safeSubstring(
        startIndex = 0,
        endIndex = 100
    )
    ?.let(TextWrapper::PlainText)
    ?: if (emptyIfAbsent) TextWrapper.PlainText("")
    else TextWrapper.StringResource(resourceId = R.string.untitled)

internal fun NoteDomainModel.getSubtitleTextWrapper(): TextWrapper = when (val firstContent = noteContent.getOrNull(0)) {
    is NoteContentDomainModel.Text -> firstContent.content
        .substringAfter(
            delimiter = "\n",
            missingDelimiterValue = "",
        )
        .takeIf { it.isNotBlank() }
        ?.trimStart { it == '\n' || it.isWhitespace() }
        ?.substringBefore(delimiter = "\n")
        ?.safeSubstring(
            startIndex = 0,
            endIndex = 100
        )
        ?.let(TextWrapper::PlainText)
        ?: TextWrapper.StringResource(resourceId = R.string.no_additional_text)
    is NoteContentDomainModel.Image -> TextWrapper.StringResource(resourceId = R.string.image)
    is NoteContentDomainModel.Audio -> TextWrapper.StringResource(resourceId = R.string.audio)
    null -> TextWrapper.StringResource(resourceId = R.string.no_additional_text)
}
