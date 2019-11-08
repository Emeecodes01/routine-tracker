package com.mobigod.routinechecks.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime


@Entity(tableName = "routine_table", primaryKeys = ["routine_title"])
data class Routine (
    //@PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "routine_title") val title: String,
    @ColumnInfo(name = "routine_description")val description: String,
    @ColumnInfo(name = "routine_frequency")val frequency: Int = 0,
    @ColumnInfo(name = "routine_frequency_type")val frequencyType: String = "",
    @ColumnInfo(name = "start_time") val startTime: DateTime,
    @ColumnInfo(name = "is_recurring") val isRecurring: Boolean = false,
    @ColumnInfo(name = "routine_status") var isDone: Boolean = false,
    @ColumnInfo(name = "status") var isCancelled: Boolean = false
)

