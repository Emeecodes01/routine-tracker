package com.mobigod.routinechecks.data

import com.mobigod.routinechecks.data.local.PreferenceManager
import com.mobigod.routinechecks.data.local.RoutineDb
import com.mobigod.routinechecks.data.models.Routine
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(var preferenceManager: PreferenceManager, val database: RoutineDb?) {

    fun addRoutine(routine: Routine): Completable {
        return database!!.routineDAO().saveRoutine(routine)
    }

    fun updateRoutine(routine: Routine){
        return database!!.routineDAO().updateRoutine(routine)
    }

    fun markRoutineAsNotDone(title: String): Completable {
        return database!!.routineDAO().updateRoutineWithId(title, false)
    }

    fun markRoutineAsDone(title: String): Completable {
        return  database!!.routineDAO().updateRoutineWithId(title, true)
    }

    fun deleteRoutine(routine: Routine): Completable {
        return database!!.routineDAO().deleteRoutineWithId(routine.title)
    }

    fun getAllRoutine(): Observable<List<Routine>>{
        return database!!.routineDAO().getAllRoutines()
    }

    fun getRoutine(title: String): Single<Routine> {
        return  database!!.routineDAO().getRoutine(title)
    }


    /***
     * This is meant to be called only in a worker thread e.g work manager
     * @param id -> uid of the routine
     */
    fun getRoutineBlocking(title: String): Routine {
        return database!!.routineDAO().getRoutineBlocking(title)
    }

    fun increaseDoneRoutineCount() {
        preferenceManager.totalDoneRoutineCount += 1
    }

    fun increaseUnDoneRoutineCount() {
        preferenceManager.totalUnDoneRoutineCount += 1
    }

    fun increaseRoutineCount() {
        preferenceManager.totalRoutineCount += 1
    }


    fun getDoneRoutineCount() =
        preferenceManager.totalDoneRoutineCount


    fun getUnDoneRoutineCount() =
        preferenceManager.totalUnDoneRoutineCount


    fun getRoutineCount() =
        preferenceManager.totalRoutineCount

}