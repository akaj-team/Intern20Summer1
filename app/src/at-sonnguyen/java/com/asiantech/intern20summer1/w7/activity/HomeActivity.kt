package com.asiantech.intern20summer1.w7.activity

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.PlantDatabase
import com.asiantech.intern20summer1.w7.extension.openVegetableFragment
import com.asiantech.intern20summer1.w7.extension.replaceFragment
import com.asiantech.intern20summer1.w7.fragment.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-sonnguyen`.nav_header.view.*
import kotlinx.android.synthetic.`at-sonnguyen`.toolbar.*
import kotlinx.android.synthetic.`at-sonnguyen`.w7_activity_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var database: PlantDatabase? = null
    internal val wormedPlantFragment = WormedPlantFragment.newInstance()
    internal val lackedWaterPlantFragment = LackedWaterPlantFragment.newInstance()
    internal val comingHarvestPlant = ComingHarvestPlant.newInstance()
    internal val plantGardenFragment = PlantGardenFragment.newInstance()
    private var fragmentId = 1
    companion object{
        internal const val FRAGMENT_ID_KEY = "fragment key"
        internal const val PLANT_GARDEN_ID = 1
        internal const val COMING_HARVEST_ID = 2
        internal const val WORMED_PLANT_ID = 3
        internal const val LACKED_WATER_ID = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w7_activity_home)
        database = PlantDatabase.getInstance(applicationContext)
        initToolBar()
        replaceFragment(R.id.flContent, PlantGardenFragment.newInstance())
        initView()
        initNavigationHeaderData()
    }

    private fun initToolBar(){
        supportActionBar?.hide()
        setSupportActionBar(toolbar)
        nvView?.setNavigationItemSelectedListener(this)
    }

    private fun initNavigationHeaderData() {
        val user = database?.userDao()?.getAllUser()
        val header = nvView.getHeaderView(0)
        user?.let {
            header.tvUsername.text = it.username
            header.tvUniversity.text = it.university
            if (it.imgUri != "") {
                header.imgAvatarHeader.setImageURI(Uri.parse(it.imgUri))
            } else {
                header.imgAvatarHeader.setImageResource(R.drawable.ic_baseline_account_circle_24)
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initView() {
        val toggle =
            ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.w7_open, R.string.w7_close)
        drawer_layout?.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_first_fragment -> {
                fragmentId = PLANT_GARDEN_ID
                sendIdToDetailFragment(fragmentId)
                openVegetableFragment(PlantGardenFragment())
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_second_fragment -> {
                fragmentId = PLANT_GARDEN_ID
                sendIdToDetailFragment(fragmentId)
               PlantDialogFragment.newInstance().show(supportFragmentManager,"")
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_third_fragment ->{
                fragmentId = COMING_HARVEST_ID
                sendIdToDetailFragment(fragmentId)
                openVegetableFragment(ComingHarvestPlant.newInstance())
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_fourth_fragment -> {
                fragmentId = WORMED_PLANT_ID
                sendIdToDetailFragment(fragmentId)
                openVegetableFragment(WormedPlantFragment.newInstance())
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_fifth_fragment -> {
                fragmentId = LACKED_WATER_ID
                sendIdToDetailFragment(fragmentId)
                openVegetableFragment(LackedWaterPlantFragment.newInstance())
                drawer_layout.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }
    private fun sendIdToDetailFragment(id : Int){
        val plantDetailFragment = PlantDetailFragment()
        val bundle = Bundle();
        bundle.putInt(FRAGMENT_ID_KEY, id);
        plantDetailFragment.arguments = bundle;
    }
}
