package com.github.zharovvv.compose.sandbox.ui.pager

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign
import kotlin.math.sqrt

@Composable
fun DraggableSurface(
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(color),
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    maxTopOffset: Dp = 0.dp,
    maxBottomOffset: Dp = 0.dp,
    dragProgressUp: (progress: Float) -> Unit = {},
    dragProgressDown: (progress: Float) -> Unit = {},
    onDragStopped: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier = containerModifier,
        contentAlignment = Alignment.Center
    ) {
        val animatableOffsetY = remember { Animatable(0f) }
        val coroutineScope = rememberCoroutineScope()
        val maxTopOffsetF = with(LocalDensity.current) { maxTopOffset.toPx() }
        val maxBottomOffsetF = with(LocalDensity.current) { maxBottomOffset.toPx() }
        Surface(
            modifier = modifier
                .offset { IntOffset(0, animatableOffsetY.value.roundToInt()) }
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        val offsetY = animatableOffsetY.value
                        val calculatedOffsetY = calculateOffset(offsetY, delta)
                        val range = -maxTopOffsetF.roundToInt()..maxBottomOffsetF.roundToInt()
                        if (calculatedOffsetY.roundToInt() in range) {
                            coroutineScope.launch {
                                if (calculatedOffsetY < 0.0f) {
                                    val upProgress = abs(calculatedOffsetY / maxTopOffsetF)
                                    dragProgressUp.invoke(upProgress)
                                } else {
                                    val downProgress = abs(calculatedOffsetY / maxBottomOffsetF)
                                    dragProgressDown.invoke(downProgress)
                                }
                                animatableOffsetY.snapTo(calculatedOffsetY)
                            }
                        }
                    },
                    onDragStopped = {
                        onDragStopped.invoke()
                        animatableOffsetY.animateOffsetReset()
                    }
                ),
            shape = shape,
            color = color,
            contentColor = contentColor,
            border = border,
            elevation = elevation,
            content = content
        )
    }
}

private fun calculateOffset(offset: Float, delta: Float): Float {
    return offset + sign(delta) * sqrt(0.5f * abs(delta))
}

private suspend fun Animatable<Float, AnimationVector1D>.animateOffsetReset(
): AnimationResult<Float, AnimationVector1D> = animateTo(
    targetValue = 0f,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )
)

