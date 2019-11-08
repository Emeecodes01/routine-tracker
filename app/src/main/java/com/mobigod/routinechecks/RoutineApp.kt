package com.mobigod.routinechecks

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.facebook.stetho.Stetho
import com.mobigod.routinechecks.di.DaggerAppComponent
import com.mobigod.routinechecks.utils.work.MyWorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import net.danlew.android.joda.JodaTimeAndroid
import javax.inject.Inject

class RoutineApp: Application(), HasAndroidInjector{

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject lateinit var workerFactory: MyWorkerFactory


    override fun onCreate(){
        super.onCreate()

        //time/date lib
        JodaTimeAndroid.init(this)

        //Stetho set up
        Stetho.initializeWithDefaults(this)

        //Init the dagger component
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)


        //Registers a custom worker factory
        WorkManager.initialize(this,
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build())
    }

    override fun androidInjector(): AndroidInjector<Any> = activityDispatchingAndroidInjector
}