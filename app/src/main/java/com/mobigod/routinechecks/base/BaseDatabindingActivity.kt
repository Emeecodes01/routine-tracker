package com.mobigod.routinechecks.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.mobigod.routinechecks.features.routine.LoadingDialog
import com.mobigod.routinechecks.utils.extensions.longToastWith

/**
 * ALL Classes that have data binding in them should extend this class
 */
abstract class BaseDatabindingActivity<T: ViewDataBinding>: BaseActivity() {

    private lateinit var loadingDialog: LoadingDialog
    lateinit var viewDataBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        performDataBinding()
        super.onCreate(savedInstanceState)

        loadingDialog = LoadingDialog()
    }

    /**
     * Set contentview via data binding
     */
    open fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutRes())
    }

    /**
     * This method should be call first inside initComponent method
     * @see initComponents
     */
    fun getBinding(): T = viewDataBinding

    /**
     * @param message show a toast message
     */
    fun showMessage(message: String?) {
        longToastWith(message!!)
        //Snackbar.make(viewDataBinding.root, message!!, Snackbar.LENGTH_SHORT).show()
    }

    /**
     * Use to perform loading
     */
    fun showLoading() {
        loadingDialog.show(supportFragmentManager, "loading_dialog")
    }


    fun hideLoading() {
        loadingDialog.dismiss()
    }

}