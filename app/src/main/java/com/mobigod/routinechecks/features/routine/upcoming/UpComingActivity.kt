package com.mobigod.routinechecks.features.routine.upcoming

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobigod.routinechecks.R
import com.mobigod.routinechecks.base.BaseDatabindingActivity
import com.mobigod.routinechecks.data.models.Routine
import com.mobigod.routinechecks.databinding.UpcomingLayoutBinding
import com.mobigod.routinechecks.features.routine.RoutineActivity
import com.mobigod.routinechecks.features.routine.RoutineViewModel
import com.mobigod.routinechecks.features.routine.adapter.RoutineAdapter
import com.mobigod.routinechecks.utils.Resource
import com.mobigod.routinechecks.utils.Status
import com.mobigod.routinechecks.utils.extensions.hide
import com.mobigod.routinechecks.utils.extensions.show
import dagger.android.AndroidInjection
import javax.inject.Inject

class UpComingActivity: BaseDatabindingActivity<UpcomingLayoutBinding>() {

    @Inject
    lateinit var routineViewModel: RoutineViewModel

    lateinit var binding: UpcomingLayoutBinding

    private val routineAdapter = RoutineAdapter()

    override fun getLayoutRes(): Int = R.layout.upcoming_layout



    override fun initComponents() {
        binding = getBinding()

        initviews()
        initListeners()

        subscribeToLiveData()

        routineViewModel.getAllRoutinesIn12Hours()

    }



    private fun subscribeToLiveData() {
        routineViewModel.routineB412hrsLiveData.observe(this,
            Observer<Resource<List<Routine>>>{
                routine ->
                when(routine.status) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        hideLoading()
                        binding.upcomingRv.show()
                        binding.emptyView.hide()
                        routineAdapter.addRoutine(routine.data)
                    }

                    Status.ERROR -> {
                        hideLoading()
                        binding.upcomingRv.hide()
                        binding.emptyView.show()
                        binding.errTv.text = routine.message
                    }
                }
            })
    }



    private fun initListeners() {
        routineAdapter.routineAdapterListener = object : RoutineAdapter.RoutineAdapterInterface {
            override fun onRoutineClicked(routine: Routine) {
                //Navigate to routine details page
            }

            override fun onEditRoutineClicked(routine: Routine) {
                //goto edit routine
            }

        }
    }


    private fun initviews() {

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.upcoming)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.upcomingRv.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@UpComingActivity, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(this@UpComingActivity)
            adapter = routineAdapter
        }
    }



    override fun injectActivity() {
        AndroidInjection.inject(this)
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun getToolbarTitle(): Int = R.string.upcoming

    companion object {
        fun start(context: Context) {
            Intent(context, UpComingActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }
}