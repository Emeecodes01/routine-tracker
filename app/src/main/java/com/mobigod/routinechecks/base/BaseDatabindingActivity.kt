package com.mobigod.routinechecks.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDatabindingActivity<T: ViewDataBinding>: BaseActivity() {

    lateinit var viewDataBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        performDataBinding()
        super.onCreate(savedInstanceState)

    }

    open fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutRes())
    }

    fun getBinding(): T = viewDataBinding

}