package com.mobigod.routinechecks.utils.work

import android.content.Context
import androidx.work.RxWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): Worker
}