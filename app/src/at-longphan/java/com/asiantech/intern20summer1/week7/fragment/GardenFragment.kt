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
import com.asiantech.intern20summer1.week7.dto.PlantAndCultivation
import com.asiantech.intern20summer1.week7.model.PlantRecyclerViewItem
import com.asiantech.intern20summer1.week7.other.ID_KEY
import com.asiantech.intern20summer1.week7.other.ModeGarden
import com.asiantech.intern20summer1.week7.other.USER_DATA_PREFS
import kotlinx.android.synthetic.`at-longphan`.fragment_plant.*

class GardenFragment : Fragment() {

    private lateinit var adapter: PlantAdapter
    private val plantRecyclerViews = mutableListOf<PlantRecyclerViewItem>()
    private var plantAndCultivations: List<PlantAndCultivation>? = null
    private var database: PlantRoomDatabase? = null
    private var mode: String? = null

    companion object {

        private const val MODE_KEY = "modeGarden"
        fun newInstance(modeGarden: String) = GardenFragment().apply {
            arguments = Bundle().apply {
                putString(MODE_KEY, modeGarden)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mode = arguments?.getString(MODE_KEY)
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
        adapter.onItemViewClick = { cultivationId, position ->
            val fragment = PlantDetailFragment.newInstance(cultivationId)
            fragmentManager?.beginTransaction()?.add(R.id.frameLayoutActivityHome, fragment.apply {
                onDelete = {
                    plantRecyclerViews.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            })
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
        plantAndCultivations = database?.cultivationDao()?.getAllCultivationByUserId(userId)
        loadModeList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadModeList() {
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
                            if (i.checkAboutToHarvest()) {
                                plantRecyclerViews.add(i.getPlantRecyclerViewItem())
                            }
                        }
                    }
                    ModeGarden.WORMED -> {
                        for (i in it) {
                            if (i.checkWormed()) {
                                plantRecyclerViews.add(i.getPlantRecyclerViewItem())
                            }
                        }
                    }
                    ModeGarden.DEHYDRATED -> {
                        for (i in it) {
                            if (i.checkDehydrated()) {
                                plantRecyclerViews.add(i.getPlantRecyclerViewItem())
                            }
                        }
                    }
                }
            } else {
                rlEmpty?.visibility = View.VISIBLE
            }
        }
    }
}
