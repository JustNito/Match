package com.smallgames.match.data

import android.content.Context
import com.smallgames.match.R
import com.smallgames.match.data.Storage
import javax.inject.Inject

class Preferences @Inject constructor(val context: Context): Storage {

    private val sharedPref = context.getSharedPreferences(
        context.resources.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    override fun getBestTime(): Int =
        sharedPref.getInt(context.resources.getString(R.string.saved_best_time_key), Int.MAX_VALUE)

    override fun setBestTime(time: Int) {
        with(sharedPref.edit()) {
            putInt(context.resources.getString(R.string.saved_best_time_key), time)
            apply()
        }
    }
}