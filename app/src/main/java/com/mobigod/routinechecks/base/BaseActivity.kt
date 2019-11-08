package com.mobigod.routinechecks.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mobigod.routinechecks.R


/**
 * All Classes that don't have data binding should extend this directly (
 * NOTE: SET CONTENT VIEW IN YOUR SUB CLASS
 */
abstract class BaseActivity: AppCompatActivity() {

    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        injectActivity()
        super.onCreate(savedInstanceState)
       // setContentView(getLayoutRes())
        initToolbar()
        initComponents()

    }

    /***
     * Classes that extend this class should provide the layout resource
     * @see R.layout.loading_layout
     */
    @LayoutRes
    abstract fun getLayoutRes(): Int

    /***
     * This gets called for classes to initialise their resources
     */
    abstract fun initComponents()


    /***
     * When you have dagger injection to a subclass of this, perform injection here
     * @see AndroidInjection.inject(this)
     */
    abstract fun injectActivity()

    /***
     * Provide your toolbar title in form of R.string.xyz
     */
    abstract fun getToolbarTitle(): Int


    /**
     * To show slide animation on start
     */
    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    /***
     * Show slide animation when finished
     */
    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    protected fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    protected fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }


    /**
     * Initialise toolbar for classes that has them
     */
    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            if (supportActionBar != null) {
                //setDisplayHomeEnabled(true)
            }
            title = resources.getString(getToolbarTitle())
        }
    }

}