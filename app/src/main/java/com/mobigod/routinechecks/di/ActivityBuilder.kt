package com.mobigod.routinechecks.di

import com.mobigod.routinechecks.SplashActivity
import com.mobigod.routinechecks.features.routine.AddRoutineActivity
import com.mobigod.routinechecks.features.routine.RoutineActivity
import com.mobigod.routinechecks.features.routine.RoutineModule
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


}