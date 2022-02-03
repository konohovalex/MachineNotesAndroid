package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.model.ButtonData
import ru.konohovalex.core.ui.model.TextWrapper

private enum class SwipeDirection {
    Left,
    Initial,
    Right,
}

@ExperimentalMaterialApi
@Composable
fun ThemedSnackbarHost(
    messageTextWrapper: TextWrapper,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
    actionButtonTextWrapper: TextWrapper = TextWrapper.StringResource(R.string.retry),
    onActionButtonClick: (() -> Unit)? = null,
    onDismissed: () -> Unit,
) {
    var size by remember { mutableStateOf(Size.Zero) }
    val swipeableState = rememberSwipeableState(SwipeDirection.Initial)
    val width = remember(size) {
        if (size.width == 0f) 1f
        else size.width
    }
    if (swipeableState.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                when (swipeableState.currentValue) {
                    SwipeDirection.Right, SwipeDirection.Left -> snackbarHostState.currentSnackbarData?.dismiss()
                    else -> return@onDispose
                }
            }
        }
    }
    val xOffset = with(LocalDensity.current) {
        swipeableState.offset.value.toDp()
    }

    SnackbarHost(
        snackbarHostState,
        snackbar = { snackbarData ->
            ThemedSnackbar(
                snackbarData = snackbarData,
                messageTextWrapper = messageTextWrapper,
                actionButtonTextWrapper = actionButtonTextWrapper,
                hasActionButton = onActionButtonClick != null,
                modifier = modifier,
                xOffset = xOffset,
            )
        },
        modifier = Modifier
            .onSizeChanged {
                size = Size(
                    width = it.width.toFloat(),
                    height = it.height.toFloat(),
                )
            }
            .swipeable(
                state = swipeableState,
                anchors = mapOf(
                    -width to SwipeDirection.Left,
                    0f to SwipeDirection.Initial,
                    width to SwipeDirection.Right,
                ),
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
    )

    LaunchedEffect(messageTextWrapper, onActionButtonClick) {
        coroutineScope.launch {
            when (
                snackbarHostState.showSnackbar(
                    message = "",
                    duration = SnackbarDuration.Indefinite,
                )
            ) {
                SnackbarResult.ActionPerformed -> onActionButtonClick?.invoke()
                SnackbarResult.Dismissed -> onDismissed.invoke()
            }
        }
    }
}

@Composable
private fun ThemedSnackbar(
    snackbarData: SnackbarData,
    messageTextWrapper: TextWrapper,
    actionButtonTextWrapper: TextWrapper,
    hasActionButton: Boolean,
    modifier: Modifier,
    xOffset: Dp,
) {
    ThemedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(Theme.paddings.contentSmall)
            .offset(x = xOffset),
        borderColor = Theme.palette.errorColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Theme.paddings.contentSmall),
            horizontalArrangement = Arrangement.spacedBy(Theme.paddings.contentMedium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MessageText(messageTextWrapper)

            if (hasActionButton) {
                ActionButton(
                    snackbarData = snackbarData,
                    actionButtonTextWrapper = actionButtonTextWrapper,
                )
            }
        }
    }
}

@Composable
private fun RowScope.MessageText(messageTextWrapper: TextWrapper) {
    ThemedText(
        themedTextType = ThemedTextType.BODY,
        textWrapper = messageTextWrapper,
        modifier = Modifier
            .weight(1f),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun ActionButton(
    snackbarData: SnackbarData,
    actionButtonTextWrapper: TextWrapper,
) {
    ThemedButton(
        buttonData = ButtonData.Outlined(
            onClick = snackbarData::performAction,
            content = listOf(ButtonData.Content.Text(actionButtonTextWrapper)),
        ),
    )
}
