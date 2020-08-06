package com.asiantech.intern20summer1.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragment.W7FragmentRecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-vuongphan`.w7_home_activity.*

class W7HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w7_home_activity)
        actionBar?.hide()
        setSupportActionBar(toolBar)
        navView?.setNavigationItemSelectedListener(this)
        initView()
        openFragmentRecyclerView()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.imgBack -> {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.navFarmer -> {
                // DialogFragment().show(supportFragmentManager, "Vuon rau")
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.navGrowVegetable -> {
                Toast.makeText(this, "trong rau", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.navHarvest -> {
                Toast.makeText(this, "rau sap thu hoach", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.navSau -> {
                openFragmentRecyclerView()
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.navNuoc -> {
                openFragmentRecyclerView()
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }

    private fun openFragmentRecyclerView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frContainerLayout, W7FragmentRecyclerView.newInstance()).commit()
    }

    private fun initView() {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolBar
            , R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
    }
}
