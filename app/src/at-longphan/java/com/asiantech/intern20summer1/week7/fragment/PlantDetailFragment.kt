package com.asiantech.intern20summer1.week7.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.PlantRoomDatabase
import com.asiantech.intern20summer1.week7.entity.Plant
import kotlinx.android.synthetic.`at-longphan`.fragment_plant_detail.*

class PlantDetailFragment : Fragment() {

    companion object{
        fun newInstance(cultivationId: Int) = PlantDetailFragment().apply {
            arguments = Bundle().apply {
                putInt("cultivationId", cultivationId)
            }
        }
    }

    private var cultivationId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cultivationId = arguments?.getInt("cultivationId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plant_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //toolbarTitle.text = "Thông tin cây"
        tvPlantMainDescription?.text = cultivationId.toString()

        insertData?.setOnClickListener {
            val database = PlantRoomDatabase.getDatabase(requireContext())
            database?.plantDao()?.insert(Plant(
                "ehehe",
                "cay kappa",
                "1 cai cay dac biet",
                5,3,
                "https://upload.wikimedia.org/wikipedia/commons/5/55/Apple_orchard_in_Tasmania.jpg"
            ))
            Toast.makeText(context, "inssert xong", Toast.LENGTH_SHORT).show()
        }
    }
}
