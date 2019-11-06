package com.mobigod.routinechecks.features.routine

import com.mobigod.routinechecks.data.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoutineModule {

    @Provides
    fun provideRoutineViewmodel(repository: Repository)
            = RoutineViewModel(repository)
}