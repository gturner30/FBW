package com.example.fullbodychallenge.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun CircularTimerRing(
    progressFraction: Float, // 0f..1f elapsed
    ringColor: Color,
    trackColor: Color,
    modifier: Modifier = Modifier,
    strokeWidthDp: Float = 22f
) {
    Canvas(modifier = modifier.fillMaxWidth().aspectRatio(1f)) {
        val stroke = Stroke(width = strokeWidthDp * density, cap = StrokeCap.Round)
        val inset = strokeWidthDp * density / 2f
        val arcSize = Size(size.width - inset * 2, size.height - inset * 2)
        drawArc(
            color = trackColor,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = stroke,
            topLeft = Offset(inset, inset),
            size = arcSize
        )
        drawArc(
            color = ringColor,
            startAngle = -90f,
            sweepAngle = 360f * progressFraction.coerceIn(0f, 1f),
            useCenter = false,
            style = stroke,
            topLeft = Offset(inset, inset),
            size = arcSize
        )
    }
}
