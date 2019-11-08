package com.mobigod.routinechecks.utils.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mobigod.routinechecks.data.Repository
import javax.inject.Inject

class CancellationWorkFactory @Inject constructor(val repository: Repository): ChildWorkerFactory {

    override fun create(appContext: Context, params: WorkerParameters): Worker {
        return  CancellationWork(repository, appContext, params)
    }

}