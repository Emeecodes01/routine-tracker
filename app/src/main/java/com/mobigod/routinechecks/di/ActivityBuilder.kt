package com.mobigod.routinechecks.di

import com.mobigod.routinechecks.SplashActivity
import com.mobigod.routinechecks.features.routine.*
import com.mobigod.routinechecks.features.routine.upcoming.UpComingActivity
import com.mobigod.routinechecks.utils.recievers.RoutineDoneBoardcastReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun provideSplashScreenActivityInjector(): SplashActivity

    @ContributesAndroidInjector(modules = [RoutineModule::class])
    abstract fun provideRoutineActivityInjector(): RoutineActivity

    @ContributesAndroidInjector(modules = [RoutineModule::class])
    abstract fun provideRoutineEditActivityInjector(): AddRoutineActivity

    @ContributesAndroidInjector
    abstract fun provideBoardcastReceiverInjector(): RoutineDoneBoardcastReceiver

    @ContributesAndroidInjector(modules = [RoutineModule::class])
    abstract fun provideUpcomingActivityInjector(): UpComingActivity

    @ContributesAndroidInjector(modules = [RoutineModule::class])
    abstract fun provideRoutinesDetailsActivityInjector(): RoutinesDetailsActivity

}