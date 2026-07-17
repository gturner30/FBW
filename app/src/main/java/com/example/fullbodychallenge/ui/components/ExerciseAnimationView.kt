package com.example.fullbodychallenge.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import com.example.fullbodychallenge.data.AnimationStyle
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private data class FigurePose(
    val head: Offset,
    val shoulder: Offset,
    val hip: Offset,
    val handL: Offset,
    val handR: Offset,
    val footL: Offset,
    val footR: Offset
)

/** All coordinates are in a normalized 0..100 logical square, scaled to the canvas at draw time. */
private fun poseFor(style: AnimationStyle, phase: Float): FigurePose {
    val angle = phase * 2f * PI.toFloat()
    val bounce = (sin(angle) + 1f) / 2f // 0..1, smooth up-down
    val sway = sin(angle) // -1..1

    return when (style) {
        AnimationStyle.SQUAT_BOB -> FigurePose(
            head = Offset(50f, 15f + bounce * 10f),
            shoulder = Offset(50f, 25f + bounce * 10f),
            hip = Offset(50f, 55f + bounce * 15f),
            handL = Offset(35f, 30f + bounce * 15f),
            handR = Offset(65f, 30f + bounce * 15f),
            footL = Offset(40f, 90f),
            footR = Offset(60f, 90f)
        )

        AnimationStyle.LUNGE_STEP -> {
            val step = bounce * 18f
            FigurePose(
                head = Offset(50f, 18f),
                shoulder = Offset(50f, 28f),
                hip = Offset(50f, 55f + bounce * 6f),
                handL = Offset(38f, 45f),
                handR = Offset(62f, 45f),
                footL = Offset(40f - step, 90f),
                footR = Offset(60f + step * 0.3f, 90f)
            )
        }

        AnimationStyle.PUSH_UP -> {
            val lift = bounce * 12f
            FigurePose(
                head = Offset(15f, 55f - lift),
                shoulder = Offset(28f, 55f - lift),
                hip = Offset(60f, 58f),
                handL = Offset(28f, 75f),
                handR = Offset(28f, 75f),
                footL = Offset(85f, 62f),
                footR = Offset(85f, 62f)
            )
        }

        AnimationStyle.STATIC_HOLD -> {
            val shimmer = sway * 1.5f
            FigurePose(
                head = Offset(15f, 50f + shimmer),
                shoulder = Offset(28f, 50f + shimmer),
                hip = Offset(60f, 52f + shimmer),
                handL = Offset(28f, 68f),
                handR = Offset(28f, 68f),
                footL = Offset(85f, 55f),
                footR = Offset(85f, 55f)
            )
        }

        AnimationStyle.SUPERMAN -> {
            val lift = bounce * 10f
            FigurePose(
                head = Offset(15f, 50f - lift),
                shoulder = Offset(30f, 52f - lift * 0.6f),
                hip = Offset(58f, 55f),
                handL = Offset(5f, 50f - lift),
                handR = Offset(5f, 50f - lift),
                footL = Offset(85f, 55f - lift),
                footR = Offset(85f, 55f - lift)
            )
        }

        AnimationStyle.ARM_RAISE -> {
            val armY = 40f - bounce * 20f
            FigurePose(
                head = Offset(50f, 15f),
                shoulder = Offset(50f, 25f),
                hip = Offset(50f, 55f),
                handL = Offset(30f, armY),
                handR = Offset(70f, armY),
                footL = Offset(42f, 90f),
                footR = Offset(58f, 90f)
            )
        }

        AnimationStyle.ROW_PULL -> {
            val reach = 25f - bounce * 18f
            FigurePose(
                head = Offset(50f, 15f),
                shoulder = Offset(50f, 25f),
                hip = Offset(50f, 55f),
                handL = Offset(50f - reach, 32f),
                handR = Offset(50f + reach, 32f),
                footL = Offset(42f, 90f),
                footR = Offset(58f, 90f)
            )
        }

        AnimationStyle.ARM_CIRCLE -> {
            val r = 16f
            FigurePose(
                head = Offset(50f, 15f),
                shoulder = Offset(50f, 25f),
                hip = Offset(50f, 55f),
                handL = Offset(50f - r * cos(angle), 25f + r * sin(angle)),
                handR = Offset(50f + r * cos(angle), 25f + r * sin(angle)),
                footL = Offset(42f, 90f),
                footR = Offset(58f, 90f)
            )
        }

        AnimationStyle.BRIDGE_LIFT -> {
            val lift = bounce * 12f
            FigurePose(
                head = Offset(15f, 60f),
                shoulder = Offset(30f, 60f),
                hip = Offset(55f, 60f - lift),
                handL = Offset(30f, 75f),
                handR = Offset(30f, 75f),
                footL = Offset(78f, 80f),
                footR = Offset(78f, 80f)
            )
        }

        AnimationStyle.LEG_RAISE -> {
            val lift = bounce * 25f
            FigurePose(
                head = Offset(15f, 60f),
                shoulder = Offset(28f, 60f),
                hip = Offset(55f, 62f),
                handL = Offset(28f, 60f),
                handR = Offset(28f, 60f),
                footL = Offset(85f, 80f - lift),
                footR = Offset(85f, 80f - lift)
            )
        }

        AnimationStyle.TORSO_TWIST -> {
            val twist = sway * 15f
            FigurePose(
                head = Offset(50f + twist, 25f),
                shoulder = Offset(50f + twist, 35f),
                hip = Offset(50f, 65f),
                handL = Offset(35f + twist, 40f),
                handR = Offset(65f + twist, 40f),
                footL = Offset(42f, 90f),
                footR = Offset(58f, 90f)
            )
        }

        AnimationStyle.MOUNTAIN_CLIMBER -> {
            val drive = bounce * 20f
            FigurePose(
                head = Offset(15f, 50f),
                shoulder = Offset(28f, 50f),
                hip = Offset(58f, 52f),
                handL = Offset(28f, 68f),
                handR = Offset(28f, 68f),
                footL = Offset(85f - drive, 62f),
                footR = Offset(85f, 62f)
            )
        }
    }
}

/**
 * A small looping stick-figure animation showing the exercise's movement
 * pattern. Pure procedural vector drawing - no bitmaps, no network, nothing
 * to license.
 */
@Composable
fun ExerciseAnimationView(
    style: AnimationStyle,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "exerciseAnim")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = LinearEasing)
        ),
        label = "phase"
    )

    Canvas(modifier = modifier) {
        val pose = poseFor(style, phase)
        val scaleX = size.width / 100f
        val scaleY = size.height / 100f
        fun Offset.toCanvas() = Offset(x * scaleX, y * scaleY)

        val strokeWidth = size.minDimension * 0.05f
        val headRadius = size.minDimension * 0.09f

        drawLine(color, pose.shoulder.toCanvas(), pose.hip.toCanvas(), strokeWidth, cap = StrokeCap.Round)
        drawLine(color, pose.shoulder.toCanvas(), pose.handL.toCanvas(), strokeWidth, cap = StrokeCap.Round)
        drawLine(color, pose.shoulder.toCanvas(), pose.handR.toCanvas(), strokeWidth, cap = StrokeCap.Round)
        drawLine(color, pose.hip.toCanvas(), pose.footL.toCanvas(), strokeWidth, cap = StrokeCap.Round)
        drawLine(color, pose.hip.toCanvas(), pose.footR.toCanvas(), strokeWidth, cap = StrokeCap.Round)
        drawCircle(color, radius = headRadius, center = pose.head.toCanvas())
    }
}
