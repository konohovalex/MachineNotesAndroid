package ru.konohovalex.core.presentation.arch.vieweffect

import androidx.lifecycle.LiveData
import ru.konohovalex.core.presentation.utils.SingleLiveEvent

class ViewEffectPublisherDelegate<E : ViewEffect> : ViewEffectPublisher<E> {
    private val _viewEffect = SingleLiveEvent<E?>()
    override val viewEffect: LiveData<E?> = _viewEffect

    override fun publish(viewEffect: E?) {
        _viewEffect.value = viewEffect
    }
}
