package ru.konohovalex.core.presentation.arch.effect

import androidx.lifecycle.LiveData

interface EffectPublisher<E : Effect> {
    val effect: LiveData<E?>

    fun publishEffect(effect: E?)
}
