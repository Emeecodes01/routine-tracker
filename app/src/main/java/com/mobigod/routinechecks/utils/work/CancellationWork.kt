package com.mobigod.routinechecks.utils.work

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.RxWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mobigod.routinechecks.data.Repository
import io.reactivex.Single

class CancellationWork(val repository: Repository, context: Context, workerParameters: WorkerParameters):
    Worker(context, workerParameters) {


    override fun doWork(): Result {
        val title = inputData.getString("title")!!

        //get the routine and change it to false
        val routine = repository.getRoutineBlocking(title)

        if (!routine.isDone) {
            val newObject = routine.copy(isCancelled = true)
            repository.increaseUnDoneRoutineCount()
            repository.updateRoutine(newObject)
        }

        return  Result.success()
    }

}