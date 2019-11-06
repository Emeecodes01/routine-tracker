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



    companion object {
        const val FIRST_TIME_KEY = "first_time"
    }

}