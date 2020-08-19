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
import com.asiantech.intern20summer1.week7.model.PlantRecyclerViewItem
import com.asiantech.intern20summer1.week7.other.*
import kotlinx.android.synthetic.`at-longphan`.fragment_plant.*

class GardenFragment : Fragment() {

    private lateinit var adapter: PlantAdapter
    private val plantRecyclerViews = mutableListOf<PlantRecyclerViewItem>()
    private var database: PlantRoomDatabase? = null
    private var mode: String? = null

    companion object {

        private const val MODE = "modeGarden"
        fun newInstance(modeGarden: String) = GardenFragment().apply {
            arguments = Bundle().apply {
                putString(MODE, modeGarden)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mode = arguments?.getString(MODE)
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

    @SuppressLint("NewApi", "SimpleDateFormat")
    private fun initData() {
        val sharePref = requireContext().getSharedPreferences(USER_DATA_PREFS, Context.MODE_PRIVATE)
        val userId = sharePref.getInt(ID_KEY, 0)
        database = PlantRoomDatabase.getDatabase(requireContext())
        val plantAndCultivations = database?.cultivationDao()?.getAllCultivationByUserId(userId)

        plantAndCultivations?.let {
            if (it.isNotEmpty()) {
                rlEmpty?.visibility = View.INVISIBLE
                when (mode) {
                    ModeGarden.DEFAULT -> {
                        for (i in it) {
                            plantRecyclerViews.add(i.getPlantRecyclerViewItem())
                        }
                    }
                    ModeGarden.ABOUT_TO_HARVEST -> {
                        for (i in it) {
                            it.getAboutToHarvestPlants(i, plantRecyclerViews)
                        }
                    }
                    ModeGarden.WORMED -> {
                        for (i in it) {
                            it.getWormedPlants(i, plantRecyclerViews)
                        }
                    }
                    ModeGarden.DEHYDRATED -> {
                        for (i in it) {
                            it.getDehydratedPlants(i, plantRecyclerViews)
                        }
                    }
                }
            } else {
                rlEmpty?.visibility = View.VISIBLE
            }
        }
    }
}
