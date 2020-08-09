package com.asiantech.intern20summer1.w7.main.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.main.MainFarmActivity
import com.asiantech.intern20summer1.w7.model.CultivationModel
import kotlinx.android.synthetic.`at-huybui`.fragment_information_tree.*

class TreeInformationFragment : Fragment() {

    private var dataBase: ConnectDataBase? = null

    companion object {
        private const val KEY_POS = "key_pos"
        internal fun newInstance(id: Int?) = TreeInformationFragment().apply {
            arguments = Bundle().apply {
                id?.let { putInt(KEY_POS, it) }
            }
        }
    }

    var cultivation: CultivationModel? = null

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
        cultivation = dataBase?.cultivationDao()?.getCultivation(arguments?.getInt(KEY_POS))
        val plant = dataBase?.plantDao()?.getPlant(cultivation?.plantId)
        detail_imgCultivation?.setImageURI(Uri.parse(plant?.imageUri))
        tv1?.text = plant?.name
        tv2?.text = plant?.description
    }

    private fun initListener() {
        handleForButtonBackListener()
        handleForButtonClearPlant()
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
                showToast("Nhổ Cây Thành Công")
                fragmentManager?.popBackStack()
                val fragment = TreeRecyclerFragment.newInstance()
                (activity as MainFarmActivity).handleReplaceFragment(
                    fragment,
                    parent = R.id.containerMain
                )
            }
        }
    }

    private fun showToast(text: Any) {
        Toast.makeText(requireContext(), text.toString(), Toast.LENGTH_SHORT).show()
    }
}
