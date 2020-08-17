package com.asiantech.intern20summer1.week7.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.PlantRoomDatabase
import com.asiantech.intern20summer1.week7.entity.Cultivation
import kotlinx.android.synthetic.`at-longphan`.activity_home_w7.*
import kotlinx.android.synthetic.`at-longphan`.fragment_plant_new_dialog.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PlantNewDialogFragment : DialogFragment() {

    private var plantSelect: String? = null
    private var database: PlantRoomDatabase? = null
    private var plants: MutableList<String>? = null

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

        database = PlantRoomDatabase.getDatabase(requireContext())
        plants = database?.plantDao()?.getAllId()
        plants?.add(0, "Loại rau muốn trồng")

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
                Toast.makeText(
                    context,
                    parent.getItemAtPosition(position).toString(),
                    Toast.LENGTH_SHORT
                ).show()

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
                Toast.makeText(context, "Please choose one", Toast.LENGTH_SHORT).show()
            }
        }

        handleButtonOkListener()
        handleImageCloseListener()
    }

    private fun handleImageCloseListener(){
        imgCloseDialog?.setOnClickListener {
            navigationViewWeek7?.setCheckedItem(R.id.navGarden)
            fragmentManager?.popBackStack()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleButtonOkListener() {
        btnOkPlantNewWeek7?.setOnClickListener {
            val sharePref =
                requireContext().getSharedPreferences("UserDataPrefs", Context.MODE_PRIVATE)
            val userId = sharePref.getInt("userId", 0)
            val timeNow = DateTimeFormatter
                .ofPattern("dd/MM/yy HH'h'mm")
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
            Toast.makeText(requireContext(), "trong cay thanh cong", Toast.LENGTH_SHORT).show()
        }
    }
}
