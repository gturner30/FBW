package com.example.fullbodychallenge.data

/**
 * Which procedural stick-figure animation represents an exercise's movement
 * pattern. Rendered by ui/components/ExerciseAnimationView.kt - pure vector
 * drawing, no image assets, so there's nothing to download or license.
 */
enum class AnimationStyle {
    SQUAT_BOB,
    LUNGE_STEP,
    PUSH_UP,
    STATIC_HOLD,
    SUPERMAN,
    ARM_RAISE,
    ROW_PULL,
    ARM_CIRCLE,
    BRIDGE_LIFT,
    LEG_RAISE,
    TORSO_TWIST,
    MOUNTAIN_CLIMBER
}
