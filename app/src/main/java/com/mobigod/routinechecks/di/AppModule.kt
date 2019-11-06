package com.mobigod.routinechecks.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.mobigod.routinechecks.data.Repository
import com.mobigod.routinechecks.data.local.PreferenceManager
import com.mobigod.routinechecks.data.local.RoutineDb
import com.mobigod.routinechecks.rx.AppSchdulersImpl
import com.mobigod.routinechecks.rx.AppSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppName() = "Routine App"


    @Provides
    @Singleton
    fun provideAppContext(app: Application): Context = app


    @Provides
    @Singleton
    fun provideSharedPreference(context: Context) =
        context.getSharedPreferences("routine_app_pref", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAndroidSchedulers(): AppSchedulers = AppSchdulersImpl()


    @Provides
    @Singleton
    fun providePreferenceManager(sharedPreferences: SharedPreferences)
            = PreferenceManager(sharedPreferences)

    @Provides
    @Singleton
    fun provideRoutineDb(context: Context) = RoutineDb.getAppDataBase(context)

    @Provides
    fun provideRepository(preferenceManager: PreferenceManager, db: RoutineDb?)
        = Repository(preferenceManager, db)




}