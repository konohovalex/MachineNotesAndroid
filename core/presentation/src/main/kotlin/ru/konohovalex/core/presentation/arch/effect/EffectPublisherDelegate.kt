package ru.konohovalex.core.presentation.arch.effect

import androidx.lifecycle.LiveData
import ru.konohovalex.core.presentation.utils.SingleLiveEvent

class EffectPublisherDelegate<E : Effect> : EffectPublisher<E> {
    private val _effect = SingleLiveEvent<E?>()
    override val effect: LiveData<E?> = _effect

    override fun publishEffect(effect: E?) {
        _effect.value = effect
    }
}
