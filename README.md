# Full Body Challenge

A daily bodyweight workout app for Android. Pick a day type (Push / Pull /
Legs / Shoulders / Core), choose a difficulty from 1–5, and log each
exercise by dragging your finger around the circular dial — one full lap
per set, just like the push-up challenge apps this was modeled on. Timed
holds (planks, wall sits) get a countdown ring instead of a drag dial.

No equipment required beyond a wall and something to sit on. No burpees,
anywhere, ever.

## How to open and run it

1. Install **Android Studio** (free, from developer.android.com) if you
   don't already have it.
2. Open Android Studio → **Open** → select this `FullBodyChallenge` folder.
3. Let Gradle sync (first sync downloads dependencies — needs internet).
4. Plug in an Android phone (with USB debugging on) or start an emulator
   from Android Studio's Device Manager.
5. Click **Run ▶**. The app installs and launches.

No signing, no Play Store setup needed to just run it on a device.

## Project layout

```
app/src/main/java/com/example/fullbodychallenge/
  data/            Workout content — every exercise, all 5 difficulty
                    variants, rep/hold targets. WorkoutPlans.kt is the
                    file to edit to change exercises or add day types.
  ui/screens/       Home, workout list, exercise counter, summary screens
  ui/components/    The circular dial widgets (drag-to-count + timer ring)
  viewmodel/        App state: selected day, difficulty, session progress
  navigation/       Screen routing
```

## Customizing the workout content

Open `data/WorkoutPlans.kt`. Each exercise is an `ExerciseTemplate` with:
- `variantNames` — 5 names, one per difficulty level (easiest → hardest)
- `targetPerLevel` — 5 numbers: reps (or seconds, if `isTimed = true`)
- `sets` — how many laps of the dial complete the exercise
- `equipmentNote` — optional text shown above the dial

To add a 6th day type, add a case to the `DayType` enum and a matching
list in the `plans` map.

## Notes on the build files

This is a standard Gradle/Kotlin/Jetpack Compose project (Compose BOM
2024.06, Kotlin 1.9.24, minSdk 26 / targetSdk 34). It was written and
reviewed carefully but **not compiled in this environment** (no Android
SDK or network access to Google's Maven repo here) — if Android Studio's
first sync flags anything (usually just a dependency version bump it
wants), it's normal and one-click-fixable from the sync error panel.
