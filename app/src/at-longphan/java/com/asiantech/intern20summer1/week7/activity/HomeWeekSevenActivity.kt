package com.asiantech.intern20summer1.week7.activity

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.fragment.GardenFragment
import com.asiantech.intern20summer1.week7.fragment.PlantNewDialogFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-longphan`.activity_home_w7.*

class HomeWeekSevenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toggle: ActionBarDrawerToggle
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_w7)
        configStatusBar()

        navView.setNavigationItemSelectedListener(this)

        setSupportActionBar(toolbarWeeK7)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarTitle.text = "Garden"

        initActionBar()
        toggle = ActionBarDrawerToggle(
            this, drawerLayoutWeek7, toolbarWeeK7,
            R.string.text_description_open_drawer, R.string.text_description_close_drawer
        )
        drawerLayoutWeek7.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        addPlantRecyclerViewFragment()
    }

    /*
    // create top right button more
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_navigation_w7, menu)
        return true
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navGarden -> {
                // ko ko dong nay la crash
                GardenFragment.newInstance()

                Toast.makeText(this, "Vườn cây", Toast.LENGTH_SHORT).show()
                toolbarTitle.text = "Garden"
            }
            R.id.navPlantNew -> {
                Toast.makeText(this, "Trồng cây", Toast.LENGTH_SHORT).show()

                val fragmentManager = supportFragmentManager
                val fragment = PlantNewDialogFragment.newInstance()
                fragment.show(fragmentManager,null)

                /*val transaction = fragmentManager.beginTransaction()
                transaction.add(
                    R.id.navHostFragment,
                    PlantNewDialogFragment()
                )
                    .addToBackStack(null)
                transaction.commit()*/

                toolbarTitle.text = "Trồng cây"
            }
            R.id.navAboutToHarvest -> {
                Toast.makeText(this, "Cây sắp thu hoạch", Toast.LENGTH_SHORT).show()
                toolbarTitle.text = "Cây sắp thu hoạch"
            }
            R.id.navWormedPlant -> {
                Toast.makeText(this, "Cây bị sâu", Toast.LENGTH_SHORT).show()
                toolbarTitle.text = "Cây bị sâu"
            }
            R.id.navDehydratedPlant -> {
                Toast.makeText(this, "Cây bị thiếu nước", Toast.LENGTH_SHORT).show()
                toolbarTitle.text = "Cây bị thiếu nước"
            }
        }
        drawerLayoutWeek7?.closeDrawer(GravityCompat.START)
        return true
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun addPlantRecyclerViewFragment() {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(
            R.id.navHostFragment,
            GardenFragment()
        )
        transaction.commit()
    }

    private fun initActionBar() {

        val actionBar = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_hamburger)
        }
    }
}
