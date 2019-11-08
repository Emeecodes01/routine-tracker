package com.mobigod.routinechecks.data.local

import androidx.room.*
import com.mobigod.routinechecks.data.models.Routine
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface RoutineDAO {

    @Query("SELECT * FROM routine_table")
    fun getAllRoutines(): Observable<List<Routine>>

    @Query("SELECT * FROM routine_table WHERE routine_title =:title")
    fun getRoutine(title: String): Single<Routine>

    @Query("SELECT * FROM routine_table WHERE routine_title =:title")
    fun getRoutineBlocking(title: String): Routine

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRoutine(routine: Routine): Completable

    @Update
    fun updateRoutine(routine: Routine)

    @Query("UPDATE routine_table SET routine_status =:status WHERE routine_title =:title")
    fun updateRoutineWithId(title: String, status: Boolean): Completable

    @Query("DELETE FROM routine_table WHERE routine_title =:title")
    fun deleteRoutineWithId(title: String): Completable

}