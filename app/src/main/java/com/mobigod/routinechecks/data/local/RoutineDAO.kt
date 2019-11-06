package com.mobigod.routinechecks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobigod.routinechecks.data.models.Routine
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface RoutineDAO {

    @Query("SELECT * FROM routine_table")
    fun getAllRoutines(): Observable<List<Routine>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRoutine(routine: Routine): Completable

}