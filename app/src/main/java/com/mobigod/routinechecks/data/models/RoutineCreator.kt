package com.mobigod.routinechecks.data.models

import org.joda.time.DateTime
import java.util.*

class RoutineCreator {
    private var title: String = ""
    private var description: String = ""
    private var routineFrequencyType: String = ""
    private var routineFrequencyNumber: String = ""
    private var isRecurring: Boolean = false
    private var hour: Int = 0
    private var minute: Int = 0
    private var seconds: Int = 0
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    //This is similar to builder design pattern
    fun setTitle(title: String) = apply { this.title = title }
    fun setDescription(des: String) = apply { this.description = des }
    fun setRoutineFrequencyType(feqType: String) = apply { this.routineFrequencyType = feqType }
    fun setRoutineFrequencyNumber(feqNum: String) = apply { this.routineFrequencyNumber = feqNum }
    fun setIsRecurring(recurring: Boolean) = apply { this.isRecurring = recurring }
    fun setHour(hr: Int) = apply { this.hour = hr }
    fun setMinute(min: Int) = apply { this.minute = min }
    fun setSeconds(secs: Int) = apply { this.seconds = secs }
    fun setYear(yr: Int) = apply { this.year = yr }
    fun setMonth(mon: Int) = apply { this.month = mon }
    fun setDay(day: Int) = apply { this.day = day }



    //Creates a routine object
    fun create(): Routine {
        val startTimeDate = DateTime(year, month, day, hour, minute, seconds)

        return  if (!isRecurring) Routine (
            title = title,
            description = description,
            startTime = startTimeDate)

        else Routine (title = title, description = description, frequencyType = routineFrequencyType,
            frequency = routineFrequencyNumber.toInt(),
            isRecurring = isRecurring,
            startTime = startTimeDate
        )


    }
}