package com.mobigod.routinechecks.features.routine

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.mobigod.routinechecks.R
import com.mobigod.routinechecks.base.BaseDatabindingActivity
import com.mobigod.routinechecks.databinding.RoutinesDetailsLayoutBinding
import com.mobigod.routinechecks.utils.Resource
import com.mobigod.routinechecks.utils.Status
import dagger.android.AndroidInjection
import javax.inject.Inject

class RoutinesDetailsActivity: BaseDatabindingActivity<RoutinesDetailsLayoutBinding>(){

    @Inject
    lateinit var routineViewModel: RoutineViewModel


    lateinit var binding: RoutinesDetailsLayoutBinding

    override fun getLayoutRes(): Int = R.layout.routines_details_layout

    override fun initComponents() {
        binding = getBinding()

        subscribeToLiveData()
        routineViewModel.getPendingRoutine()
    }

    private fun subscribeToLiveData() {
        routineViewModel.routinePendingLiveData.observe(this,
            Observer<Resource<Int>>{
                routines ->
                when(routines.status) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        hideLoading()
                        calculateData(routines.data!!)
                    }

                    Status.ERROR -> {
                        hideLoading()
                        showMessage(routines.message)
                    }
                }
            })
    }

    private fun calculateData(pendingRoutineCount: Int) {
        val routineCounts = routineViewModel.getAllRoutineCounts()
        val totalRoutineCount = routineCounts[0]
        val totalDoneRoutineCount = routineCounts[1]
        val totalUnDoneRoutine = routineCounts[2]
        var message = "Try more..."

        val totalPercentCount = totalRoutineCount - pendingRoutineCount//use this value to cal. percentage
        val percentageDone = (totalDoneRoutineCount.toFloat() / totalPercentCount.toFloat()) * 100
        if (percentageDone > 70) {
            message = "Good Job"
            binding.performMv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_like))
        }else {
            binding.performMv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_sad))
        }

        binding.messageTv.text = message
        binding.percentTv.text = "$percentageDone"
        binding.doneRoutineTv.text = "$totalDoneRoutineCount"
        binding.missedRoutineTv.text = "$totalUnDoneRoutine"
    }


    override fun injectActivity() {
        AndroidInjection.inject(this)
    }

    override fun getToolbarTitle(): Int = R.string.app_name


    companion object {
        fun start(context: Context){
            Intent(context, RoutinesDetailsActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }
}