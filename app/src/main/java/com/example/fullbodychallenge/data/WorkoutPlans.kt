package com.example.fullbodychallenge.data

/**
 * All workout content lives here. Every exercise is bodyweight-only or uses
 * something already in most homes (a wall, a step, a sturdy chair). Nothing
 * here is a burpee, and nothing ever will be.
 */
object WorkoutPlans {

    private val push = listOf(
        ExerciseTemplate(
            id = "push_pushup",
            variantNames = listOf(
                "Wall Push-ups",
                "Incline Push-ups",
                "Knee Push-ups",
                "Standard Push-ups",
                "Diamond Push-ups"
            ),
            isTimed = false,
            targetPerLevel = listOf(10, 12, 12, 15, 15),
            sets = 3,
            equipmentNote = "Level 1-2 use a wall or sturdy table edge"
        ),
        ExerciseTemplate(
            id = "push_pike",
            variantNames = listOf(
                "Shoulder Taps (plank)",
                "Incline Pike Push-ups",
                "Pike Push-ups",
                "Elevated Pike Push-ups",
                "Deep Pike Push-ups"
            ),
            isTimed = false,
            targetPerLevel = listOf(16, 10, 10, 12, 12),
            sets = 3,
            equipmentNote = "Level 4-5: feet on a step or sturdy chair"
        ),
        ExerciseTemplate(
            id = "push_dip",
            variantNames = listOf(
                "Assisted Bench Dips (bent knees)",
                "Bench Dips",
                "Straight-leg Bench Dips",
                "Elevated Bench Dips",
                "Slow Tempo Bench Dips (4s down)"
            ),
            isTimed = false,
            targetPerLevel = listOf(8, 10, 12, 12, 10),
            sets = 3,
            equipmentNote = "Needs a sturdy chair, step or low bench"
        ),
        ExerciseTemplate(
            id = "push_plank_hold",
            variantNames = listOf(
                "Incline Plank Hold",
                "Knee Plank Hold",
                "Plank Hold",
                "Plank with Shoulder Taps",
                "Plank with Arm Reach"
            ),
            isTimed = true,
            targetPerLevel = listOf(20, 25, 30, 40, 45),
            sets = 3
        )
    )

    private val pull = listOf(
        ExerciseTemplate(
            id = "pull_superman",
            variantNames = listOf(
                "Superman Hold",
                "Superman Pulses",
                "Superman Row (arms pull back)",
                "Superman with Leg Lift",
                "Superman Full Extension Hold"
            ),
            isTimed = false,
            targetPerLevel = listOf(8, 10, 12, 12, 15),
            sets = 3
        ),
        ExerciseTemplate(
            id = "pull_ytw",
            variantNames = listOf(
                "Prone Y Raises",
                "Prone T Raises",
                "Prone W Raises",
                "Y-T-W Combo (1 rep = all three)",
                "Y-T-W Combo, Slow Tempo"
            ),
            isTimed = false,
            targetPerLevel = listOf(10, 10, 10, 8, 8),
            sets = 3
        ),
        ExerciseTemplate(
            id = "pull_towel_row",
            variantNames = listOf(
                "Standing Towel Door Row (light lean)",
                "Standing Towel Door Row",
                "Low Towel Door Row (deep lean)",
                "Single-arm Towel Door Row",
                "Slow Tempo Single-arm Row (3s pull)"
            ),
            isTimed = false,
            targetPerLevel = listOf(10, 12, 12, 10, 10),
            sets = 3,
            equipmentNote = "Optional: a towel looped around a sturdy door handle. No towel? Substitute extra Superman Rows."
        ),
        ExerciseTemplate(
            id = "pull_reverse_snow_angel",
            variantNames = listOf(
                "Reverse Snow Angels (small range)",
                "Reverse Snow Angels",
                "Reverse Snow Angels (full range)",
                "Reverse Snow Angels with 2s Hold",
                "Reverse Snow Angels, Slow Tempo"
            ),
            isTimed = false,
            targetPerLevel = listOf(10, 12, 12, 10, 10),
            sets = 3
        )
    )

    private val legs = listOf(
        ExerciseTemplate(
            id = "legs_squat",
            variantNames = listOf(
                "Assisted Squats (hold a chair)",
                "Bodyweight Squats",
                "Bodyweight Squats (full depth)",
                "Pause Squats (2s at bottom)",
                "Jump Squats"
            ),
            isTimed = false,
            targetPerLevel = listOf(12, 15, 18, 15, 15),
            sets = 3
        ),
        ExerciseTemplate(
            id = "legs_lunge",
            variantNames = listOf(
                "Split Squat Hold (static)",
                "Reverse Lunges",
                "Forward Lunges",
                "Walking Lunges",
                "Jumping Lunges"
            ),
            isTimed = false,
            targetPerLevel = listOf(8, 10, 12, 12, 10),
            sets = 3,
            equipmentNote = "Reps count per leg"
        ),
        ExerciseTemplate(
            id = "legs_bridge",
            variantNames = listOf(
                "Glute Bridge",
                "Glute Bridge with 2s Squeeze",
                "Single-leg Glute Bridge",
                "Elevated Single-leg Glute Bridge",
                "Single-leg Glute Bridge, Slow Tempo"
            ),
            isTimed = false,
            targetPerLevel = listOf(12, 12, 10, 10, 10),
            sets = 3
        ),
        ExerciseTemplate(
            id = "legs_wall_sit",
            variantNames = listOf(
                "Wall Sit (high)",
                "Wall Sit",
                "Wall Sit (90°)",
                "Wall Sit with Calf Raises",
                "Single-leg Wall Sit Hold"
            ),
            isTimed = true,
            targetPerLevel = listOf(20, 30, 40, 45, 30),
            sets = 3
        )
    )

    private val shoulders = listOf(
        ExerciseTemplate(
            id = "shoulder_arm_circle",
            variantNames = listOf(
                "Arm Circles (small)",
                "Arm Circles (large)",
                "Arm Circles, Slow Tempo",
                "Arm Circles with Hold Every 5",
                "Extended Arm Circles, Slow Tempo"
            ),
            isTimed = true,
            targetPerLevel = listOf(20, 25, 30, 35, 40),
            sets = 2
        ),
        ExerciseTemplate(
            id = "shoulder_pike_walk",
            variantNames = listOf(
                "Downward Dog Hold",
                "Downward Dog Shoulder Taps",
                "Pike Walk-outs",
                "Pike Push-ups",
                "Elevated Pike Push-ups"
            ),
            isTimed = false,
            targetPerLevel = listOf(20, 12, 8, 10, 10),
            sets = 3
        ),
        ExerciseTemplate(
            id = "shoulder_plank_reach",
            variantNames = listOf(
                "Knee Plank Arm Reach",
                "Plank Arm Reach",
                "Plank Shoulder Taps",
                "Plank Shoulder Taps (fast)",
                "Plank Walk-out to Shoulder Tap"
            ),
            isTimed = false,
            targetPerLevel = listOf(10, 12, 16, 20, 12),
            sets = 3
        ),
        ExerciseTemplate(
            id = "shoulder_isometric_hold",
            variantNames = listOf(
                "Wall Angel Hold",
                "Wall Angels",
                "Wall Angels, Slow Tempo",
                "Standing Y-Raise Hold",
                "Standing Y-Raise Hold, Extended"
            ),
            isTimed = true,
            targetPerLevel = listOf(20, 25, 30, 30, 40),
            sets = 3
        )
    )

    private val core = listOf(
        ExerciseTemplate(
            id = "core_plank",
            variantNames = listOf(
                "Knee Plank Hold",
                "Plank Hold",
                "Plank Hold (long)",
                "Plank with Alternating Leg Lift",
                "Plank with Alternating Arm+Leg Lift"
            ),
            isTimed = true,
            targetPerLevel = listOf(20, 30, 45, 40, 40),
            sets = 3
        ),
        ExerciseTemplate(
            id = "core_leg_raise",
            variantNames = listOf(
                "Bent-Knee Leg Raises",
                "Leg Raises",
                "Slow Leg Raises",
                "Leg Raises with 2s Hold at Top",
                "Hanging-style Leg Raises (lying, full extension)"
            ),
            isTimed = false,
            targetPerLevel = listOf(10, 12, 12, 10, 12),
            sets = 3
        ),
        ExerciseTemplate(
            id = "core_russian_twist",
            variantNames = listOf(
                "Seated Twists (feet down)",
                "Russian Twists (feet down)",
                "Russian Twists (feet lifted)",
                "Weighted Russian Twists",
                "Russian Twists, Slow Tempo (feet lifted)"
            ),
            isTimed = false,
            targetPerLevel = listOf(16, 20, 20, 20, 24),
            sets = 3,
            equipmentNote = "Level 4: optional light book or water bottle in hands"
        ),
        ExerciseTemplate(
            id = "core_mountain_climber",
            variantNames = listOf(
                "Slow Mountain Climbers",
                "Mountain Climbers",
                "Mountain Climbers (faster)",
                "Cross-body Mountain Climbers",
                "Mountain Climbers, Fast Tempo"
            ),
            isTimed = true,
            targetPerLevel = listOf(20, 25, 30, 30, 40),
            sets = 3
        )
    )

    val plans: Map<DayType, List<ExerciseTemplate>> = mapOf(
        DayType.PUSH to push,
        DayType.PULL to pull,
        DayType.LEGS to legs,
        DayType.SHOULDERS to shoulders,
        DayType.CORE to core
    )

    /** Suggests a day type from the current weekday, cycling through the 5 types. */
    fun suggestedDayType(isoDayOfWeek: Int): DayType {
        // isoDayOfWeek: 1=Monday .. 7=Sunday. Sat/Sun repeat Core & Legs as active recovery.
        return when (isoDayOfWeek) {
            1 -> DayType.PUSH
            2 -> DayType.PULL
            3 -> DayType.LEGS
            4 -> DayType.SHOULDERS
            5 -> DayType.CORE
            6 -> DayType.LEGS
            else -> DayType.CORE
        }
    }
}
