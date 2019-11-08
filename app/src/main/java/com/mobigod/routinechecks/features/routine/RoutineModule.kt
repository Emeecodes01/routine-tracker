package com.mobigod.routinechecks.features.routine

import com.mobigod.routinechecks.data.Repository
import com.mobigod.routinechecks.rx.AppSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoutineModule {

    @Provides
    fun provideRoutineViewmodel(schedulers: AppSchedulers, repository: Repository)
            = RoutineViewModel(repository, schedulers)
}