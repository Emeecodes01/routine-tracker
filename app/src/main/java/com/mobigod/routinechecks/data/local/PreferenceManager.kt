package com.mobigod.routinechecks.data.local

import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceManager(var sharedPreferences: SharedPreferences) {

    var isFirstTime: Boolean = false
    set(value) {
        sharedPreferences.edit().putBoolean(FIRST_TIME_KEY, value).apply()
        field = value
    }
    get() {
        return  sharedPreferences.getBoolean(FIRST_TIME_KEY, false)
    }


    var totalRoutineCount: Int = 0
    set(value) {

        sharedPreferences.edit().putInt(ROUTINE_COUNT_KEY, value).apply()
        field = value
    }
    get() {
        return sharedPreferences.getInt(ROUTINE_COUNT_KEY, 0)
    }


    var totalDoneRoutineCount: Int = 0
        set(value) {
            sharedPreferences.edit().putInt(DONE_ROUTINE_COUNT_KEY, value).apply()
            field = value
        }
        get() {
            return sharedPreferences.getInt(DONE_ROUTINE_COUNT_KEY, 0)
        }


    var totalUnDoneRoutineCount: Int = 0
        set(value) {
            sharedPreferences.edit().putInt(UNDONE_ROUTINE_COUNT_KEY, value).apply()
            field = value
        }
        get() {
            return sharedPreferences.getInt(UNDONE_ROUTINE_COUNT_KEY, 0)
        }




    companion object {
        const val FIRST_TIME_KEY = "first_time"
        const val ROUTINE_COUNT_KEY = "routine_count"
        const val DONE_ROUTINE_COUNT_KEY = "done_routine_count"
        const val UNDONE_ROUTINE_COUNT_KEY = "undone_routine_count"
    }

}