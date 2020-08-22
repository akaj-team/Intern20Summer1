package com.asiantech.intern20summer1.week7.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.PlantRoomDatabase
import com.asiantech.intern20summer1.week7.entity.Cultivation
import com.asiantech.intern20summer1.week7.other.CustomSpinner
import com.asiantech.intern20summer1.week7.other.DATETIME_FORMAT
import com.asiantech.intern20summer1.week7.other.ID_KEY
import com.asiantech.intern20summer1.week7.other.USER_DATA_PREFS
import kotlinx.android.synthetic.`at-longphan`.fragment_plant_new_dialog.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PlantNewDialogFragment : DialogFragment() {

    private var plantSelect: String? = null
    private var database: PlantRoomDatabase? = null
    private var plants: MutableList<String>? = null
    internal var reloadData: (bool: Boolean) -> Unit = {}

    companion object {
        internal fun newInstance() = PlantNewDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plant_new_dialog, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initSpinner()
        handleButtonOkListener()
        handleImageCloseListener()
        handleSpinner()
    }

    private fun initSpinner() {
        val arrayAdapter = activity?.let {
            plants?.let { arrayString ->
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_spinner_dropdown_item,
                    arrayString
                )
            }
        }

        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            @SuppressLint("ResourceType")
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                plantSelect = parent.getItemAtPosition(position).toString()

                btnOkPlantNewWeek7?.let {
                    if (position == 0) {
                        it.isEnabled = false
                        it.setBackgroundResource(R.drawable.bg_button_next_register_disable_w7)
                        it.setTextColor(Color.parseColor(resources.getString(R.color.colorButtonNextRegisterDisableWeek7Text)))
                    } else {
                        it.isEnabled = true
                        it.setBackgroundResource(R.drawable.bg_button_next_register_enable_w7)
                        it.setTextColor(Color.parseColor(resources.getString(R.color.colorButtonNextRegisterEnableWeek7Text)))
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    context,
                    getString(R.string.toast_nothing_seclected_spinner),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initData() {
        database = PlantRoomDatabase.getDatabase(requireContext())
        plants = database?.plantDao()?.getAllId()
        plants?.add(0, getString(R.string.hint_spinner_week_7))
    }

    private fun handleImageCloseListener() {
        imgCloseDialog?.setOnClickListener {
            //navigationViewWeek7?.setCheckedItem(R.id.navGarden)
            reloadData.invoke(false)
            fragmentManager?.popBackStack()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleButtonOkListener() {
        btnOkPlantNewWeek7?.setOnClickListener {
            insertNewPlant()
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_plant_new_successfully_description),
                Toast.LENGTH_SHORT
            ).show()
            reloadData.invoke(true)
            fragmentManager?.popBackStack()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertNewPlant() {
        val sharePref =
            requireContext().getSharedPreferences(USER_DATA_PREFS, Context.MODE_PRIVATE)
        val userId = sharePref.getInt(ID_KEY, 0)
        val timeNow = DateTimeFormatter
            .ofPattern(DATETIME_FORMAT)
            .format(LocalDateTime.now())

        plantSelect?.let { plantId ->
            database?.cultivationDao()?.insert(
                Cultivation(
                    0,
                    userId,
                    plantId,
                    timeNow,
                    timeNow
                )
            )
        }
    }

    private fun handleSpinner() {
        spinner?.setSpinnerEventsListener(object : CustomSpinner.OnSpinnerEventsListener {
            override fun onSpinnerOpened(spinner: Spinner?) {
                imgIconSpinner?.animate()?.rotationBy(90F)?.duration = 300
            }

            override fun onSpinnerClosed(spinner: Spinner?) {
                imgIconSpinner?.animate()?.rotationBy(-90F)?.duration = 300
            }
        })
    }

    private fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()
}
