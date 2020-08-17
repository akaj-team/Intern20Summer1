package com.asiantech.intern20summer1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.database.Cultivation
import com.asiantech.intern20summer1.database.Plant
import com.asiantech.intern20summer1.database.VegetableDB

class VegetableDetailFragment : Fragment() {
    private var dataBase: VegetableDB? = null
    private var cultivation: Cultivation? = null
    private var plant: Plant? = null

    companion object {
        private const val KEY_POS_ID = "key_pos_id"
        internal fun newInstance(id: Int?) = VegetableDetailFragment().apply {
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

}
