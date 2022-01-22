package ru.konohovalex.core.presentation.arch.vieweffect

import androidx.lifecycle.LiveData

interface ViewEffectPublisher<E : ViewEffect> {
    val viewEffect: LiveData<E?>

    fun publish(viewEffect: E?)
}
