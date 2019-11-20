package com.mobigod.routinechecks.features.routine

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mobigod.routinechecks.R
import com.mobigod.routinechecks.base.BaseDatabindingActivity
import com.mobigod.routinechecks.data.models.RoutineCreator
import com.mobigod.routinechecks.databinding.ActivityEditRoutineBinding
import com.mobigod.routinechecks.utils.Resource
import com.mobigod.routinechecks.utils.Status
import com.mobigod.routinechecks.utils.extensions.hide
import com.mobigod.routinechecks.utils.extensions.isShowing
import com.mobigod.routinechecks.utils.extensions.show
import dagger.android.AndroidInjection
import javax.inject.Inject
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Handler


class AddRoutineActivity: BaseDatabindingActivity<ActivityEditRoutineBinding>(),
    TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    lateinit var binding: ActivityEditRoutineBinding

    private var routineCreator = RoutineCreator()

    override fun injectActivity() {
        AndroidInjection.inject(this)
    }

    @Inject
    lateinit var routineViewModel: RoutineViewModel

    override fun getLayoutRes(): Int = R.layout.activity_edit_routine

    override fun initComponents() {
        binding = getBinding()

        initViews()
        setUpListeners()
        subscribeToLiveData()

    }


    override fun getToolbarTitle(): Int = R.string.Create


    /**
     * Subscribe to all live data in the viewmodel class, this serves as a communication point btw the view and view model
     * Also for state management
     * */
    private fun subscribeToLiveData() {
        routineViewModel.routineDbLiveData.observe(this,
            Observer<Resource<String>> {
                resource ->
                when(resource.status) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        hideLoading()
                        showMessage(resource.data)
                        finish()
                    }

                    Status.ERROR -> {
                        hideLoading()
                        showMessage(resource.message)
                    }

                }
            })
    }



    private fun setUpListeners() {
        binding.time.setOnClickListener {

            val now = Calendar.getInstance()
            val tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
            )
            tpd.show(supportFragmentManager, "Timepickerdialog")
        }

        binding.date.setOnClickListener {

            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            )
            dpd.show(supportFragmentManager, "Datepickerdialog")
        }
    }


    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        routineCreator.setHour(hourOfDay)
        routineCreator.setMinute(minute)
        routineCreator.setSeconds(second)

        val selectedTime = getSelectedTime(hourOfDay, minute)
        binding.selectedTime.text = selectedTime
    }


    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        routineCreator.setYear(year)
        routineCreator.setMonth(monthOfYear + 1)
        routineCreator.setDay(dayOfMonth)

        val date = getSelectedDate(year, monthOfYear, dayOfMonth)
        binding.selectedDate.text = date
    }


    /**
     * Convert the hour and minute into a formatted string like 10:30 am
     * @param hourOfDay
     * @param minute
     * */
    private fun getSelectedTime(hourOfDay: Int, minute: Int): String {
        val localTime = LocalTime(hourOfDay, minute)
        val dbf = DateTimeFormat.forPattern("hh:mm a")
        return localTime.toString(dbf)
    }



    /**
     * Convert the yr, month and day into a formatted string like 23/04/2019
     * @param yr -> Year
     * @param month -> Month
     * @param day -> Day
     * */
    private fun getSelectedDate(yr: Int, month: Int, day: Int ): String {
        val localDate = LocalDate(yr, month+1, day)
        return localDate.toString("dd/MM/yyyy")
    }

    private fun initViews() {

        binding.isRecurring.setOnCheckedChangeListener { buttonView, isChecked ->
            routineCreator.setIsRecurring(isChecked)

            //show those setter layouts
            if (isChecked) {
                binding.freqTypeLlayout.show()
            }else {
                binding.freqLlayout.hide()
                binding.freqTypeLlayout.hide()
            }
        }

        ArrayAdapter.createFromResource(this,
            R.array.frequency_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.frequencyTypeSpinner.adapter = adapter
            binding.frequencyTypeSpinner.onItemSelectedListener = frequencyTypeArrayListener
        }

    }


    fun createFrequencyAdapter(isMins: Boolean) {

        if (!binding.freqLlayout.isShowing()) {
            binding.freqLlayout.show()
        }

        if (isMins) {
            ArrayAdapter.createFromResource(this,
                R.array.mins_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.frequencyNumSpinner.adapter = adapter
                binding.frequencyNumSpinner.onItemSelectedListener = frequencyArrayListener
            }
            return
        }


        ArrayAdapter.createFromResource(this,
            R.array.frequency_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.frequencyNumSpinner.adapter = adapter
            binding.frequencyNumSpinner.onItemSelectedListener = frequencyArrayListener
        }

    }


    private val frequencyArrayListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val frequency = parent?.getItemAtPosition(position) as String
            routineCreator.setRoutineFrequencyNumber(frequency)
        }

    }



    private val frequencyTypeArrayListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val frequencyType = parent?.getItemAtPosition(position) as String
            routineCreator.setRoutineFrequencyType(frequencyType)

            binding.numOfFreqTv.text = "Select the number of $frequencyType"

            if (frequencyType == "mins") {
                createFrequencyAdapter(true)
            }else{
                createFrequencyAdapter(false)
            }
        }


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
                validateUserInput()
            }
        }
        return true
    }



    private fun validateUserInput() {
        if (binding.titleEd.text.isNotEmpty()) {
            routineCreator.setTitle(binding.titleEd.text.toString())
        }

        if (binding.descriptionEd.text!!.isNotEmpty()){
            routineCreator.setDescription(binding.descriptionEd.text.toString())
        }

        //use workmanager via the view model to save this stuff
        //create routein object and save in db

        val routine = routineCreator.create()
        if (routine.isRecurring){
            routineViewModel.setUpRecurringWorkRequest(routine)
        }else {
            routineViewModel.setUpOnTimeWorkRequest(this, routine)
        }
        routineViewModel.saveRoutineInDb(routine)
    }



    companion object {
        fun start(context: Context){
            Intent(context, AddRoutineActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }


}