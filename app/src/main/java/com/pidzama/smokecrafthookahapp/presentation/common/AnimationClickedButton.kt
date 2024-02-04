package com.pidzama.smokecrafthookahapp.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

enum class ButtonState { PRESSED, IDLE }

fun Modifier.bounceClick() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.IDLE) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.PRESSED) 0.9f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.PRESSED) {
                    waitForUpOrCancellation()
                    ButtonState.IDLE
                } else {
                    awaitFirstDown(false)
                    ButtonState.PRESSED
                }
            }
        }
}