package com.mobigod.routinechecks.di

import androidx.work.Worker
import com.mobigod.routinechecks.utils.work.*
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

/**
 * This file is needed to perform injection in your worker classes
 *
 */

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class WorkerKey(val value: KClass<out Worker>)

@Module
abstract class MyWorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(CancellationWork::class)
    internal abstract fun bindMyWorker(worker: CancellationWork): Worker


    @Binds
    @IntoMap
    @WorkerKey(CancellationWork::class)
    internal abstract fun bindMyWorkerFactory(worker: CancellationWorkFactory): ChildWorkerFactory


    @Binds
    @IntoMap
    @WorkerKey(ActualWork::class)
    internal abstract fun bindCancellationWorkFactory(worker: ActualWorkFactory): ChildWorkerFactory


    @Binds
    @IntoMap
    @WorkerKey(ReminderWork::class)
    internal abstract fun bindReminderWorkFactory(worker: ReminderWorkFactory): ChildWorkerFactory


}