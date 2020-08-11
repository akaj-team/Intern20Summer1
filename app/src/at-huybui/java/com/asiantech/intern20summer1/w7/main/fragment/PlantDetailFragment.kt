package com.asiantech.intern20summer1.w7.main.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.companion.App
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.main.MainFarmActivity
import com.asiantech.intern20summer1.w7.model.CultivationModel
import kotlinx.android.synthetic.`at-huybui`.fragment_information_tree.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/04/20
 * This is fragment class for detail fragment
 */

class PlantDetailFragment : Fragment() {

    private var dataBase: ConnectDataBase? = null

    companion object {
        private const val KEY_POS_ID = "key_pos_id"
        internal fun newInstance(id: Int?) = PlantDetailFragment().apply {
            arguments = Bundle().apply {
                id?.let { putInt(KEY_POS_ID, it) }
            }
        }
    }

    private var cultivation: CultivationModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBase = ConnectDataBase.dataBaseConnect(requireContext())
        return inflater.inflate(R.layout.fragment_information_tree, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        cultivation = dataBase?.cultivationDao()?.getCultivation(arguments?.getInt(KEY_POS_ID))
        val plant = dataBase?.plantDao()?.getPlant(cultivation?.plantId)
        if (App().isPlantWormed(plant, cultivation)) {
            tvWormed_detail?.text = getString(R.string.w7_detail_plant_is_wormed)
            tvWormed_detail?.setTextColor(Color.RED)
        } else {
            tvWormed_detail?.text = getString(R.string.w7_detail_cay_khoe_manh)
            tvWormed_detail?.setTextColor(Color.BLUE)
        }
        detail_imgCultivation?.setImageURI(Uri.parse(plant?.imageUri))
        tv2?.text = "water ring: " + cultivation?.dateWatering
        tv3?.text = cultivation?.dateCultivation
    }

    private fun initListener() {
        handleForButtonBackListener()
        handleForButtonClearPlant()
        handleForButtonWatering()
    }

    private fun handleForButtonBackListener() {
        btnBack_detail?.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun handleForButtonClearPlant() {
        btnClearPlant_detail?.setOnClickListener {
            cultivation?.let {
                dataBase?.cultivationDao()?.deleteCultivation(it)
                (activity as MainFarmActivity).apply { fragment.initData(fragment.mode) }
                showToast(getString(R.string.w7_clear_plant_complete))
                fragmentManager?.popBackStack()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun handleForButtonWatering() {
        btnWatering_detail?.setOnClickListener {
            cultivation?.let {
                val dateFormat = SimpleDateFormat(App.FORMAT_CODE_DATE)
                dataBase?.cultivationDao()?.waterPlant(it.id, dateFormat.format(Date()))
                (activity as MainFarmActivity).apply { fragment.initData(fragment.mode) }
                initView()
                showToast(getString(R.string.w7_watering_complete))
            }
        }
    }

    private fun showToast(text: Any) {
        Toast.makeText(requireContext(), text.toString(), Toast.LENGTH_SHORT).show()
    }
}
