package ru.konohovalex.feature.account.presentation.auth.validator

import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.core.utils.model.Validator
import ru.konohovalex.feature.account.presentation.R
import ru.konohovalex.feature.account.presentation.auth.model.PasswordUiModel

class PasswordValidator : Validator<PasswordUiModel, TextWrapper?> {
    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_PASSWORD_LENGTH = 16
        private const val UNIQUE_SYMBOLS_NUMBER = 1
        private const val UNIQUE_SYMBOLS_OF_EACH = 4
        private const val UNIQUE_SYMBOLS_PERCENTAGE = 1 - (UNIQUE_SYMBOLS_NUMBER.toFloat() / UNIQUE_SYMBOLS_OF_EACH)
    }

    private val punctuationRegex = Regex("[\\p{Punct}]")

    override fun validate(input: PasswordUiModel): TextWrapper? {
        return when {
            input.value.length < MIN_PASSWORD_LENGTH -> TextWrapper.StringResource(
                resourceId = R.string.min_length_limitation,
                formatArgs = arrayOf(MIN_PASSWORD_LENGTH),
            )
            input.value.length > MAX_PASSWORD_LENGTH -> TextWrapper.StringResource(
                resourceId = R.string.max_length_limitation,
                formatArgs = arrayOf(MAX_PASSWORD_LENGTH),
            )
            else -> {
                var hasUppercaseLetter = false
                var hasLowerCaseLetter = false
                var hasDigit = false
                var hasPunctuation = false

                val symbolsAmountMap = mutableMapOf<Char, Int>()

                input.value.forEach {
                    if (it.isWhitespace()) return TextWrapper.StringResource(
                        resourceId = R.string.whitespace_limitation,
                    )

                    if (!hasUppercaseLetter && it.isUpperCase()) hasUppercaseLetter = true
                    if (!hasLowerCaseLetter && it.isLowerCase()) hasLowerCaseLetter = true
                    if (!hasDigit && it.isDigit()) hasDigit = true
                    if (!hasPunctuation && it.toString().matches(punctuationRegex)) hasPunctuation = true

                    val currentSymbolAmount = symbolsAmountMap[it] ?: 0
                    if (symbolsAmountMap.containsKey(it)) symbolsAmountMap[it] = currentSymbolAmount.inc()
                    else symbolsAmountMap[it] = 1
                }

                when {
                    !hasUppercaseLetter -> TextWrapper.StringResource(
                        resourceId = R.string.uppercase_letter_limitation,
                    )
                    !hasLowerCaseLetter -> TextWrapper.StringResource(
                        resourceId = R.string.lowercase_letter_limitation,
                    )
                    !hasDigit -> TextWrapper.StringResource(
                        resourceId = R.string.digit_limitation,
                    )
                    !hasPunctuation -> TextWrapper.StringResource(
                        resourceId = R.string.symbol_limitation,
                    )
                    else -> {
                        val mostlyDuplicatingSymbolAmount =
                            if (symbolsAmountMap.isNotEmpty()) symbolsAmountMap.maxOf { it.value }
                            else 0
                        val uniqueSymbolsConditionMet = mostlyDuplicatingSymbolAmount <= input.value.length * UNIQUE_SYMBOLS_PERCENTAGE

                        TextWrapper.StringResource(
                            resourceId = R.string.unique_symbols_limitation,
                            formatArgs = arrayOf(
                                UNIQUE_SYMBOLS_NUMBER,
                                UNIQUE_SYMBOLS_OF_EACH,
                            )
                        ).takeIf { !uniqueSymbolsConditionMet }
                    }
                }
            }
        }
    }
}
