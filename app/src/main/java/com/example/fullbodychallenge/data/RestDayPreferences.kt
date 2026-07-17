package com.example.fullbodychallenge.data

import android.content.Context

/**
 * Stores which weekdays the user has marked as rest days (max 2, enforced by
 * the ViewModel, not here). Values are ISO day-of-week ints, 1=Monday..7=Sunday.
 */
class RestDayPreferences(context: Context) {
    private val prefs = context.applicationContext
        .getSharedPreferences("full_body_challenge_prefs", Context.MODE_PRIVATE)

    fun getRestDays(): Set<Int> =
        prefs.getStringSet(KEY_REST_DAYS, emptySet())
            ?.mapNotNull { it.toIntOrNull() }
            ?.toSet()
            ?: emptySet()

    fun setRestDays(days: Set<Int>) {
        prefs.edit()
            .putStringSet(KEY_REST_DAYS, days.map { it.toString() }.toSet())
            .apply()
    }

    companion object {
        private const val KEY_REST_DAYS = "rest_days"
    }
}
