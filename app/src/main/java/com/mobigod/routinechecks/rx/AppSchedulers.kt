package com.mobigod.routinechecks.rx

import io.reactivex.Scheduler

interface AppSchedulers {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}