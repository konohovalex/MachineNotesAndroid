package ru.konohovalex.core.ui.extension

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.konohovalex.core.ui.model.SwipeDirection

@Composable
inline fun <T, R> T.letCompose(crossinline block: (T) -> R): @Composable (() -> Unit) = {
    let {
        block.invoke(it)
    }
}

@ExperimentalMaterialApi
fun Modifier.simpleSwipeable(
    state: SwipeableState<SwipeDirection>,
    anchors: Map<Float, SwipeDirection>,
    reverseDirection: Boolean = false,
    thresholdFraction: Float = 0.3f,
    orientation: Orientation = Orientation.Horizontal,
) = swipeable(
    state = state,
    anchors = anchors,
    reverseDirection = reverseDirection,
    thresholds = { _, _ -> FractionalThreshold(thresholdFraction) },
    orientation = orientation,
)
