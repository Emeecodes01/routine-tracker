package com.mobigod.routinechecks.features.routine.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mobigod.routinechecks.R
import com.mobigod.routinechecks.data.models.Routine
import com.mobigod.routinechecks.databinding.RoutinesItemLayoutBinding
import com.mobigod.routinechecks.utils.extensions.getFormattedTime

class RoutineAdapter: RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {
    private var routines = mutableListOf<Routine?>()
    lateinit var binding: RoutinesItemLayoutBinding

    private var context: Context? = null

    lateinit var routineAdapterListener: RoutineAdapterInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        context = parent.context
        binding = RoutinesItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return RoutineViewHolder(binding.root)
    }

    override fun getItemCount(): Int = routines.size

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int){
        holder.bindTo(position)
    }


    fun addRoutine(list: List<Routine>?) {
        if (routines.isNotEmpty()) {
            routines.clear()
        }
        routines.addAll(list!!)
        notifyDataSetChanged()
    }


    inner class RoutineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        init {

            binding.mainLlayout.setOnClickListener {
                routineAdapterListener.onRoutineClicked(routines[adapterPosition]!!)
            }

            binding.editRoutine.setOnClickListener {
                routineAdapterListener.onEditRoutineClicked(routines[adapterPosition]!!)
            }

        }



        fun bindTo(position: Int) {
            val routine = routines[position]
            binding.routine = routine
            binding.routineTime.text = routine?.startTime?.getFormattedTime()


            if (routine!!.isCancelled) {
                binding.routineTag.text = "Missed"
                binding.routineTag.setTextColor(ContextCompat.getColor(context!!, R.color.red))
                binding.tag.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.holo_red_light))
            }else {
                //not cancelled
                if (routine.isDone) {
                    binding.routineTag.text = "Done"
                    binding.titleTv.paintFlags = binding.titleTv.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG
                    binding.tag.setBackgroundColor(ContextCompat.getColor(context!!, R.color.secondaryLightColor))
                    binding.routineTag.setTextColor(ContextCompat.getColor(context!!, android.R.color.holo_green_light))
                }else {
                    binding.routineTag.text = "Pending"
                    binding.tag.setBackgroundColor(ContextCompat.getColor(context!!, R.color.yellow))
                    binding.routineTag.setTextColor(ContextCompat.getColor(context!!, R.color.yellow))
                }


            }

        }



    }


    interface RoutineAdapterInterface {
        fun onRoutineClicked(routine: Routine)
        fun onEditRoutineClicked(routine: Routine)
    }

}