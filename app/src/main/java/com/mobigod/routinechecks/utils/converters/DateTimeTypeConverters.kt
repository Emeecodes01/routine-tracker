package com.mobigod.routinechecks.utils.converters

import androidx.room.TypeConverter
import org.joda.time.DateTime

class DateTimeTypeConverters {
    companion object {

        @TypeConverter
        @JvmStatic
        fun fromDateTime(value: DateTime): Long{
            return value.millis
        }


        @TypeConverter
        @JvmStatic
        fun toDateTime(value:Long):DateTime {
            return DateTime(value)
        }
    }


}