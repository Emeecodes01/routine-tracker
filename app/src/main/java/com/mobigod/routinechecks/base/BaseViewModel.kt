package com.mobigod.routinechecks.base

import androidx.lifecycle.ViewModel
import com.mobigod.routinechecks.data.Repository
import io.reactivex.disposables.CompositeDisposable

/**
 * Sub class to all view models
 * @see
 */
abstract class BaseViewModel(val repository: Repository): ViewModel() {

    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    protected fun handleError(throwable: Throwable) {

    }

    protected fun getErrorMessage(throwable: Throwable): String? {
        return  throwable.message
    }


}