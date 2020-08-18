package com.asiantech.intern20summer1.fragment.w7

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.database.Cultivation
import com.asiantech.intern20summer1.database.Plant
import com.asiantech.intern20summer1.database.VegetableDB
import kotlinx.android.synthetic.`at-vuongphan`.w7_vegetable_detail.*

class VegetableDetailFragment : Fragment() {
    private var dataBase: VegetableDB? = null
    private var cultivation: Cultivation? = null
    private var plant: Plant? = null

    companion object {
        private const val KEY_POS_ID = "key_pos_id"
        internal fun newInstance(id: Int?) = VegetableDetailFragment()
            .apply {
            arguments = Bundle().apply {
                id?.let { putInt(KEY_POS_ID, it) }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBase = VegetableDB.dataBaseConnect(requireContext())
        return inflater.inflate(R.layout.w7_vegetable_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initListener() {
        initBack()
        initButtonDelete()
        initForButtonSprinklers()
    }

    private fun initBack() {
        imgBackDetail?.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun initButtonDelete() {
        btnCutTree?.setOnClickListener {
            Toast.makeText(context, R.string.clear_plant_complete, Toast.LENGTH_SHORT).show()
            fragmentManager?.popBackStack()
        }
    }

    private fun initForButtonSprinklers() {
        btnSprinklers?.setOnClickListener {
            Toast.makeText(context, R.string.watering_plant_complete, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        cultivation = dataBase?.cultivationDao()?.getCultivation(arguments?.getInt(
            KEY_POS_ID
        ))
        plant = dataBase?.plantDao()?.getPlant(cultivation?.plantId)
        imgAppBarLayout?.setImageURI(Uri.parse(plant?.imageUri))
        val textCultivation = getString(R.string.text_cultivation, cultivation?.dateCultivation)
        val textWater = getString(R.string.text_watering, cultivation?.dateWatering)
    }
}
