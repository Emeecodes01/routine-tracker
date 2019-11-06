package com.mobigod.routinechecks.features.routine

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.mobigod.routinechecks.R
import com.mobigod.routinechecks.base.BaseDatabindingActivity
import com.mobigod.routinechecks.databinding.RoutineActivityLayoutBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.routine_activity_layout.*
import javax.inject.Inject

class RoutineActivity: BaseDatabindingActivity<RoutineActivityLayoutBinding>() {

    @Inject
    lateinit var routineViewModel: RoutineViewModel

    lateinit var binding: RoutineActivityLayoutBinding

    override fun injectActivity() {
        AndroidInjection.inject(this)
    }

    override fun getLayoutRes(): Int = R.layout.routine_activity_layout


    override fun initComponents() {
        binding = getBinding()

        binding.addRoutineFab.setOnClickListener {
            AddRoutineActivity.start(this)
        }

    }

    companion object {

        fun start(context: Context){
            Intent(context, RoutineActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }


}