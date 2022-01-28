package ru.konohovalex.feature.account.presentation.auth.validator

import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.core.utils.model.Validator
import ru.konohovalex.feature.account.presentation.R
import ru.konohovalex.feature.account.presentation.auth.model.PasswordUiModel

class PasswordValidator : Validator<PasswordUiModel, TextWrapper?> {
    // tbd are conditions good enough?
    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_PASSWORD_LENGTH = 32
        private const val UNIQUE_SYMBOLS_NUMBER = 1
        private const val UNIQUE_SYMBOLS_OF_EACH = 4
        private const val UNIQUE_SYMBOLS_PERCENTAGE = 1 - (UNIQUE_SYMBOLS_NUMBER.toFloat() / UNIQUE_SYMBOLS_OF_EACH)
    }

    override fun validate(input: PasswordUiModel): TextWrapper? {
        val symbolsAmountMap = mutableMapOf<Char, Int>()
        input.value.forEach {
            val currentSymbolAmount = symbolsAmountMap[it] ?: 0
            if (symbolsAmountMap.containsKey(it)) symbolsAmountMap[it] = currentSymbolAmount.inc()
            else symbolsAmountMap[it] = 1
        }
        val mostlyDuplicatingSymbolAmount =
            if (symbolsAmountMap.isNotEmpty()) symbolsAmountMap.maxOf { it.value }
            else 0
        val uniqueSymbolsConditionMet = mostlyDuplicatingSymbolAmount <= input.value.length * UNIQUE_SYMBOLS_PERCENTAGE
        return when {
            input.value.length < MIN_PASSWORD_LENGTH -> TextWrapper.StringResource(
                resourceId = R.string.min_length_limitation,
                formatArgs = arrayOf(MIN_PASSWORD_LENGTH),
            )
            input.value.length > MAX_PASSWORD_LENGTH -> TextWrapper.StringResource(
                resourceId = R.string.max_length_limitation,
                formatArgs = arrayOf(MAX_PASSWORD_LENGTH),
            )
            !uniqueSymbolsConditionMet -> TextWrapper.StringResource(
                resourceId = R.string.password_unique_symbols_limitation,
                formatArgs = arrayOf(
                    UNIQUE_SYMBOLS_NUMBER,
                    UNIQUE_SYMBOLS_OF_EACH,
                ),
            )
            else -> null
        }
    }
}
