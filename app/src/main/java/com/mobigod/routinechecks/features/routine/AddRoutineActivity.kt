package com.mobigod.routinechecks.features.routine

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.mobigod.routinechecks.R
import com.mobigod.routinechecks.base.BaseDatabindingActivity
import com.mobigod.routinechecks.databinding.ActivityEditRoutineBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class AddRoutineActivity: BaseDatabindingActivity<ActivityEditRoutineBinding>() {

    lateinit var binding: ActivityEditRoutineBinding

    @Inject
    lateinit var routineViewModel: RoutineViewModel

    override fun getLayoutRes(): Int = R.layout.activity_edit_routine

    override fun initComponents() {
        binding = getBinding()

    }

    override fun injectActivity() {
        AndroidInjection.inject(this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_routine_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.delete ->{

            }

            R.id.done -> {

            }
        }
        return true
    }

    companion object {
        fun start(context: Context) {
            Intent(context, AddRoutineActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }

}