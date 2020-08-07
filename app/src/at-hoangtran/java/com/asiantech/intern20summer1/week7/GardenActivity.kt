package com.asiantech.intern20summer1.week7

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.asiantech.intern20summer1.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-hoangtran`.activity_garden.*
import kotlinx.android.synthetic.`at-hoangtran`.toolbar.*

class GardenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garden)

        handleActionBar()
        nav_bar.setNavigationItemSelectedListener(this)
    }

    private fun handleActionBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = "Nav bar"

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            dl_garden,
            toolbar,
            (R.string.app_name),
            (R.string.app_name)
        ) {
        }
        drawerToggle.isDrawerIndicatorEnabled = true
        dl_garden.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        dl_garden.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (dl_garden.isDrawerOpen(GravityCompat.START)) {
            dl_garden.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}