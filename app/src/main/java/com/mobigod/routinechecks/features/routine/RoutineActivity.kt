package com.mobigod.routinechecks.features.routine

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobigod.routinechecks.R
import com.mobigod.routinechecks.base.BaseDatabindingActivity
import com.mobigod.routinechecks.data.models.Routine
import com.mobigod.routinechecks.databinding.RoutineActivityLayoutBinding
import com.mobigod.routinechecks.features.routine.adapter.RoutineAdapter
import com.mobigod.routinechecks.features.routine.upcoming.UpComingActivity
import com.mobigod.routinechecks.utils.Resource
import com.mobigod.routinechecks.utils.Status
import com.mobigod.routinechecks.utils.extensions.hide
import com.mobigod.routinechecks.utils.extensions.show
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.routine_activity_layout.*
import javax.inject.Inject

class RoutineActivity: BaseDatabindingActivity<RoutineActivityLayoutBinding>() {

    override fun getToolbarTitle(): Int  = R.string.app_name

    @Inject
    lateinit var routineViewModel: RoutineViewModel

    lateinit var binding: RoutineActivityLayoutBinding

    private val routineAdapter = RoutineAdapter()

    override fun injectActivity() {
        AndroidInjection.inject(this)
    }

    override fun getLayoutRes(): Int = R.layout.routine_activity_layout


    override fun initComponents() {
        binding = getBinding()

        binding.addRoutineFab.setOnClickListener {
            AddRoutineActivity.start(this)
        }

        setSupportActionBar(binding.toolbar)

        supportActionBar?.title = getString(R.string.app_name)

        ActionBarDrawerToggle(this, binding.navDrawer, binding.toolbar,
            R.string.open_drawer, R.string.close_drawer)
            .syncState()

        initViews()
        initListeners()

        subscribeToAllLiveData()
        //routineViewModel.getAllRoutines()
    }


    /***
     * init all listeners
     */
    private fun initListeners() {

        routineAdapter.routineAdapterListener = object : RoutineAdapter.RoutineAdapterInterface {
            override fun onRoutineClicked(routine: Routine) {
                //Navigate to routine details page
            }

            override fun onEditRoutineClicked(routine: Routine) {
                //goto edit routine
            }

        }


        binding.navView.setNavigationItemSelectedListener {
            menuItem ->
            when(menuItem.itemId){
                R.id.upcoming ->{
                    UpComingActivity.start(this)
                }
            }

            true
        }
    }



    private fun initViews() {

        binding.routineRv.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@RoutineActivity, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(this@RoutineActivity)
            adapter = routineAdapter
        }


    }

    override fun onResume() {
        super.onResume()
        routineViewModel.getAllRoutines()
    }

    /***
     * Receive all events here regrading the async operation you started
     */
    private fun subscribeToAllLiveData() {
        routineViewModel.routineMainLiveData.observe(this, Observer<Resource<List<Routine>>>{
            routine ->
            when(routine.status) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    hideLoading()
                    binding.emptyView.hide()
                    binding.routineRv.show()
                    routineAdapter.addRoutine(routine.data)
                }

                Status.ERROR -> {
                    // show error layout and
                    hideLoading()
                    binding.emptyView.show()
                    binding.routineRv.hide()
                    binding.errTv.text = routine.message
                }
            }
        })
    }



    companion object {
        fun start(context: Context){
            Intent(context, RoutineActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }


}