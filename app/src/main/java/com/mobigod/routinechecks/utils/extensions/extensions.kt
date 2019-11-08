package com.mobigod.routinechecks.utils.extensions

import android.app.Activity
import android.widget.Toast
import org.joda.time.DateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat


fun Activity.longToastWith(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


fun Activity.shortToastWith(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun DateTime.getFormattedTime(): String{
    val localTime = LocalTime(hourOfDay, minuteOfHour)
    val dbf = DateTimeFormat.forPattern("hh:mm a")
    return localTime.toString(dbf)
}
