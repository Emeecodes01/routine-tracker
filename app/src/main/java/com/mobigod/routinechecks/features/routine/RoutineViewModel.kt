package com.mobigod.routinechecks.features.routine

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.mobigod.routinechecks.base.BaseViewModel
import com.mobigod.routinechecks.data.Repository
import com.mobigod.routinechecks.data.models.Routine
import com.mobigod.routinechecks.rx.AppSchedulers
import com.mobigod.routinechecks.utils.Resource
import com.mobigod.routinechecks.utils.work.ActualWork
import com.mobigod.routinechecks.utils.work.CancellationWork
import com.mobigod.routinechecks.utils.work.ReminderWork
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.joda.time.*
import java.lang.Exception
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.logging.Handler

class RoutineViewModel(repo: Repository, val schedulers: AppSchedulers): BaseViewModel(repo) {

    val workRequestLiveData = MutableLiveData<String>()
    val routineDbLiveData = MutableLiveData<Resource<String>>()
    val routineMainLiveData = MutableLiveData<Resource<List<Routine>>>()
    val routineB412hrsLiveData = MutableLiveData<Resource<List<Routine>>>()

    fun setUpOnTimeWorkRequest(context: Context, routine: Routine) {
        //Create a 5mins b4 time

        val now = DateTime()

        val reminderPoint = routine.startTime.minusMinutes(5)
        val cancellationPoint = routine.startTime.plusMinutes(5)

        val reminderIntervalMilli = Interval(now, reminderPoint).toDurationMillis()
        val cancellationIntervalMilli = Interval(now, cancellationPoint).toDurationMillis()
        val actualIntervalMilli = Interval(now, routine.startTime).toDurationMillis()


        //input data for this work
        val reminderWorkData = Data.Builder()
            .putString("title", routine.title)
            .build()


        //for battery life optimization
//        val constraints = Constraints.Builder()
//            .setRequiresBatteryNotLow(true)
//            .build()

        val actualWork = OneTimeWorkRequest.Builder(ActualWork::class.java)
            .setInitialDelay(actualIntervalMilli, TimeUnit.MILLISECONDS)
            //.setConstraints(constraints)
            .setInputData(reminderWorkData).build()


        val reminderWork = OneTimeWorkRequest.Builder(ReminderWork::class.java)
            .setInitialDelay(reminderIntervalMilli, TimeUnit.MILLISECONDS)
            //.setConstraints(constraints)
            .setInputData(reminderWorkData).build()


        val cancellationWork = OneTimeWorkRequest.Builder(CancellationWork::class.java)
            .setInitialDelay(cancellationIntervalMilli, TimeUnit.MILLISECONDS)
            //.setConstraints(constraints)
            .setInputData(reminderWorkData).build()

        WorkManager.getInstance(context).enqueue(reminderWork)
        WorkManager.getInstance(context).enqueue(actualWork)
        WorkManager.getInstance(context).enqueue(cancellationWork)

    }

    fun setUpRecurringWorkRequest(routine: Routine){

    }


    /***
     * @param routine gets saved in the DB
     */
    fun saveRoutineInDb(routine: Routine) {
        compositeDisposable.add(repository.addRoutine(routine)
            .subscribeOn(schedulers.io())
            .doOnSubscribe {
                routineDbLiveData.postValue(Resource.Loading())
            }
            .observeOn(schedulers.ui())
            .subscribeBy (
                onComplete = {
                    //just to simulate loading
                    android.os.Handler().postDelayed({
                        routineDbLiveData.postValue(Resource.Success("Successfully Added to Routines"))
                    }, 500)

                },

                onError = {
                    android.os.Handler().postDelayed(
                        {
                            val errorMessage = getErrorMessage(it)
                            routineDbLiveData.postValue(Resource.Error(errorMessage))
                        }, 500
                    )

                }
            )
        )
    }


    /***
     * This just fetches all routines from the db
     */
    fun getAllRoutines() {
        compositeDisposable.add(repository.getAllRoutine()
            .subscribeOn(schedulers.io())
            .doOnSubscribe {
                routineMainLiveData.postValue(Resource.Loading())
            }
            .observeOn(schedulers.ui())
            .subscribeBy (
                onNext = {
                    routineMainLiveData.postValue(Resource.Success(it))
                },

                onError = {
                    android.os.Handler().postDelayed(
                        {
                            val errorMessage = getErrorMessage(it)
                            routineMainLiveData.postValue(Resource.Error(errorMessage))
                        }, 500
                    )

                },

                onComplete = {
                    val nu = 3
                }

            )
        )
    }


    /***
     * Gets all Routines in 12 Hours from this instant
     */
    fun getAllRoutinesIn12Hours() {
        compositeDisposable.add(repository.getAllRoutine()
            .subscribeOn(schedulers.io())
            .doOnSubscribe {
                routineB412hrsLiveData.postValue(Resource.Loading())
            }
            .observeOn(schedulers.ui())
            .subscribeBy (
                onNext = {
                    android.os.Handler().postDelayed(
                        {
                            val routines = it.filter {
                                it.startTime.isBefore(DateTime.now().plusHours(12)) && !it.isCancelled && !it.isDone
                            }
                            if (routines.isEmpty()){
                                routineB412hrsLiveData.postValue(Resource.Error("You Don't have any routines in the next 12 hours"))
                            }else{
                                routineB412hrsLiveData.postValue(Resource.Success(routines))
                            }

                        }, 500
                    )
                },

                onError = {
                    android.os.Handler().postDelayed (
                        {
                            val errorMessage = getErrorMessage(it)
                            routineB412hrsLiveData.postValue(Resource.Error(errorMessage))
                        }, 500
                    )

                }

            )
        )
    }
}