package com.mobigod.routinechecks.utils.work

import android.content.Context
import androidx.work.*
import javax.inject.Inject
import javax.inject.Provider

class MyWorkerFactory @Inject constructor (val workers: Map<Class<out Worker>,
            @JvmSuppressWildcards Provider<ChildWorkerFactory>>) : WorkerFactory() {

    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {

        val foundEntry =
            workers.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }

        val factoryProvider = foundEntry?.value
            ?: throw IllegalArgumentException("unknown worker class name: $workerClassName")

        return factoryProvider.get().create(appContext, workerParameters)
    }

}