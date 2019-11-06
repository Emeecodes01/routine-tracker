package com.mobigod.routinechecks.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobigod.routinechecks.data.models.Routine
import androidx.room.Room
import androidx.room.TypeConverters
import com.mobigod.routinechecks.utils.converters.DateTimeTypeConverters


@Database(entities = [Routine::class], version = 1)
@TypeConverters(DateTimeTypeConverters::class)
abstract class RoutineDb: RoomDatabase() {
    abstract fun routineDAO(): RoutineDAO

    companion object {
        var INSTANCE: RoutineDb? = null

        fun getAppDataBase(context: Context): RoutineDb? {
            if (INSTANCE == null) {
                synchronized(RoutineDb::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RoutineDb::class.java, "routine_db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }

}
