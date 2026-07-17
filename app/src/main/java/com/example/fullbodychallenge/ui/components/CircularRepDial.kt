package com.example.fullbodychallenge.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import kotlin.math.atan2

/**
 * A ring you drag your finger around, clockwise, to log reps - one full lap
 * equals [repsPerLap]. Dragging backward removes reps, mirroring the
 * reference app's dial-based rep counter.
 *
 * This composable is stateless about the rep count itself: it reports raw
 * signed angle deltas via [onAngleDelta] so the caller can decide how to
 * turn sweep into reps (see ExerciseCounterScreen).
 */
@Composable
fun CircularRepDial(
    progressFraction: Float, // 0f..1f, how far around the current lap the fill should show
    ringColor: Color,
    trackColor: Color,
    onAngleDelta: (Float) -> Unit,
    modifier: Modifier = Modifier,
    strokeWidthDp: Float = 22f,
    centerContent: @Composable () -> Unit
) {
    var lastAngle by remember { mutableStateOf<Float?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        lastAngle = angleOfTouch(offset, size)
                    },
                    onDragEnd = { lastAngle = null },
                    onDragCancel = { lastAngle = null }
                ) { change, _ ->
                    change.consume()
                    val current = angleOfTouch(change.position, size)
                    val prev = lastAngle
                    if (prev != null) {
                        var delta = current - prev
                        if (delta > 180f) delta -= 360f
                        if (delta < -180f) delta += 360f
                        onAngleDelta(delta)
                    }
                    lastAngle = current
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
            val stroke = Stroke(width = strokeWidthDp * density, cap = StrokeCap.Round)
            val inset = strokeWidthDp * density / 2f
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke,
                topLeft = Offset(inset, inset),
                size = androidx.compose.ui.geometry.Size(size.width - inset * 2, size.height - inset * 2)
            )
            drawArc(
                color = ringColor,
                startAngle = -90f,
                sweepAngle = 360f * progressFraction.coerceIn(0f, 1f),
                useCenter = false,
                style = stroke,
                topLeft = Offset(inset, inset),
                size = androidx.compose.ui.geometry.Size(size.width - inset * 2, size.height - inset * 2)
            )
        }
        centerContent()
    }
}

private fun angleOfTouch(offset: Offset, size: IntSize): Float {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val dx = offset.x - cx
    val dy = offset.y - cy
    return Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
}
