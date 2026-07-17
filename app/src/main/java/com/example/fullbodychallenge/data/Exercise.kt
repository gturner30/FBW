package com.example.fullbodychallenge.data

enum class DayType(val displayName: String) {
    PUSH("Push Day"),
    PULL("Pull Day"),
    LEGS("Leg Day"),
    SHOULDERS("Shoulder Day"),
    CORE("Core Day")
}

/**
 * One exercise "slot" in a workout. Each slot has 5 variant names (one per
 * difficulty level, easiest to hardest) plus a target amount per level.
 * Some exercises are rep-based (spin the dial to log reps), others are
 * timed holds (plank, wall sit) which use a countdown ring instead.
 */
data class ExerciseTemplate(
    val id: String,
    val variantNames: List<String>, // size 5, index 0 = level 1 ... index 4 = level 5
    val isTimed: Boolean,
    val targetPerLevel: List<Int>, // reps if !isTimed, seconds if isTimed. size 5
    val animation: AnimationStyle,
    val sets: Int = 3,
    val equipmentNote: String? = null // e.g. "Optional: sturdy chair or step"
) {
    fun nameForLevel(level: Int): String = variantNames[(level - 1).coerceIn(0, 4)]
    fun targetForLevel(level: Int): Int = targetPerLevel[(level - 1).coerceIn(0, 4)]
}
