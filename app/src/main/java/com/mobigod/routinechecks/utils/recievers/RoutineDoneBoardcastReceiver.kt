package com.mobigod.routinechecks.utils.recievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mobigod.routinechecks.data.Repository
import com.mobigod.routinechecks.rx.AppSchedulers
import dagger.android.AndroidInjection
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RoutineDoneBoardcastReceiver: BroadcastReceiver() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var schedulers: AppSchedulers

    val compositeDisposable = CompositeDisposable()


    override fun onReceive(context: Context?, intent: Intent?) {
        AndroidInjection.inject(this, context)

        val uid = intent?.getStringExtra("title")

        val route = repository.getRoutineBlocking(uid!!)
        val newR = route.copy(isDone = true)
        repository.updateRoutine(newR)
        repository.increaseDoneRoutineCount()

        /***
         * This is supposed to be async but for some reason i don't know, it's not updating the db when you return
         * Maybe, Single or Completable
         */
//        compositeDisposable.add(repository.getRoutine(uid!!)
//            .subscribeOn(schedulers.io())
//            .doOnSuccess {routine ->
//                if (!routine.isCancelled) {
//
//                }
//            }
//            .observeOn(schedulers.ui())
//            .subscribeBy(
//                onSuccess = {routine ->
//                    routine.isDone = true
//                    repository.updateRoutine(routine)
//                        .subscribeOn(schedulers.io())
//                        .observeOn(schedulers.ui())
//                        .subscribe({
//                            Log.i("d", "s")
//                        }, {
//
//                        })
//                    compositeDisposable.clear()
//                }
//            )
//        )



    }

}