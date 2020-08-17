package com.asiantech.intern20summer1.week7.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.PlantRoomDatabase
import com.asiantech.intern20summer1.week7.adapter.PlantAdapter
import com.asiantech.intern20summer1.week7.entity.Cultivation
import com.asiantech.intern20summer1.week7.entity.Plant
import com.asiantech.intern20summer1.week7.model.PlantRecyclerViewItem
import kotlinx.android.synthetic.`at-longphan`.fragment_plant.*
import org.json.JSONArray
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GardenFragment : Fragment() {

    private lateinit var adapter: PlantAdapter
    private val plantRecyclerViews = mutableListOf<PlantRecyclerViewItem>()
    private val plants = mutableListOf<Plant>()
    private val cultivations = mutableListOf<Cultivation>()
    private var database: PlantRoomDatabase? = null

    companion object {

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        //readJson()
        initAdapter()
        handlePlantItemClickListener()

    }

    private fun initAdapter() {
        adapter = PlantAdapter(requireContext(), plantRecyclerViews)
        recyclerViewPlant?.adapter = adapter
        recyclerViewPlant?.layoutManager = LinearLayoutManager(context)
    }

    private fun handlePlantItemClickListener() {
        adapter.onItemViewClick = { cultivationId ->
            val fragment = PlantDetailFragment.newInstance(cultivationId)
            fragmentManager?.beginTransaction()?.add(R.id.navHostFragment, fragment)
                ?.addToBackStack(null)
                ?.hide(this)
                ?.commit()
        }
    }

    @SuppressLint("NewApi")
    private fun initData() {
        val sharePref = requireContext().getSharedPreferences("UserDataPrefs", Context.MODE_PRIVATE)
        val userId = sharePref.getInt("userId", 0)
        database = PlantRoomDatabase.getDatabase(requireContext())
        val plantAndCultivations = database?.cultivationDao()?.findCultivationByUserId(userId)
        if (plantAndCultivations != null) {
            for (i in plantAndCultivations){
                plantRecyclerViews.add(i.getPlantRecyclerViewItem())
            }
        }

        /*for (i in 1..10) {
            plantRecyclerViews.add(
                PlantRecyclerViewItem(
                    i,
                    "Plant $i",
                    DateTimeFormatter
                        .ofPattern("dd/MM/yy HH'h'mm")
                        .format(LocalDateTime.now()),
                    DateTimeFormatter
                        .ofPattern("dd/MM/yy HH'h'mm")
                        .format(LocalDateTime.now()),
                    "url",
                    i % 2 == 0
                )
            )
        }*/
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun readJson() {
        val json: String?
        try {
            val inputStream = context?.assets?.open("plants.json")
            json = inputStream?.bufferedReader().use { it?.readText() }
            val jsonArray = JSONArray(json)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                plants.add(
                    Plant(
                        jsonObject.getString("plantId"),
                        jsonObject.getString("name"),
                        jsonObject.getString("description"),
                        jsonObject.getInt("growZoneNumber"),
                        jsonObject.getInt("wateringInterval"),
                        jsonObject.getString("imageUrl")
                    )
                )
                plantRecyclerViews.add(
                    PlantRecyclerViewItem(
                        i,
                        "Plant $i",
                        DateTimeFormatter
                            .ofPattern("dd/MM/yy HH'h'mm")
                            .format(LocalDateTime.now()),
                        DateTimeFormatter
                            .ofPattern("dd/MM/yy HH'h'mm")
                            .format(LocalDateTime.now()),
                        jsonObject.getString("imageUrl"),
                        i % 2 == 0
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
