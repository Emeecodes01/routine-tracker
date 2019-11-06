package com.mobigod.routinechecks.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "routine_table")
data class Routine(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "routine_title") val title: String,
    @ColumnInfo(name = "routine_description")val description: String,
    @ColumnInfo(name = "routine_frequency")val frequency: Int,
    @ColumnInfo(name = "routine_frequency_type")val frequencyType: String,
    @ColumnInfo(name = "start_time") val startTime: DateTime)
