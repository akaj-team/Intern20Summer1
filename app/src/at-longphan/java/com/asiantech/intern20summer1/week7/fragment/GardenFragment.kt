package com.asiantech.intern20summer1.week7.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.adapter.PlantAdapter
import com.asiantech.intern20summer1.week7.model.PlantRecyclerViewItem
import kotlinx.android.synthetic.`at-longphan`.fragment_plant.*
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class GardenFragment : Fragment() {

    private lateinit var adapter: PlantAdapter
    private val plants = mutableListOf<PlantRecyclerViewItem>()

    companion object{
        internal fun newInstance(): GardenFragment {
            return GardenFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSampleData()

        adapter = context?.let { PlantAdapter(it, plants) }!!

        recyclerViewPlant?.adapter = adapter
        recyclerViewPlant?.layoutManager = LinearLayoutManager(context)

        initIsLikedImageViewClickListener()

        //handleListeners()
    }

    private fun initIsLikedImageViewClickListener() {
        adapter.onItemViewClick = { plantId ->
            Toast.makeText(context, "plantId: $plantId", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("NewApi")
    private fun initSampleData() {
        //Tự phát sinh 50 dữ liệu mẫu
        for (i in 1..10) {
            plants.add(
                PlantRecyclerViewItem(
                    i,
                    "Plant $i",
                    DateTimeFormatter
                        .ofPattern("dd/MM/yy HH'h'mm")
                        .withZone(ZoneOffset.UTC)
                        .format(Instant.now()),
                    DateTimeFormatter
                        .ofPattern("dd/MM/yy HH'h'mm")
                        .withZone(ZoneOffset.UTC)
                        .format(Instant.now()),
                    "url",
                    i % 2 == 0
                )
            )
        }
    }
}
