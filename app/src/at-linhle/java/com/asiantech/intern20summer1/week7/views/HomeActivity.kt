package com.asiantech.intern20summer1.week7.views

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.extensions.changeColorStatusBar
import com.asiantech.intern20summer1.week7.fragments.DialogFragment
import com.asiantech.intern20summer1.week7.fragments.GrowPlantFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-linhle`.activity_home_toolbar.*
import kotlinx.android.synthetic.`at-linhle`.coordinator_layout.*
import kotlinx.android.synthetic.`at-linhle`.navigation_view_header.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var appDatabase: AppDatabase? = null
    private val fragment = GrowPlantFragment.newInstance()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_toolbar)
        appDatabase = AppDatabase.getInstance(this)
        handleActionBarListener()
        handleReplaceFragment(fragment, parent = R.id.flPlantViewContainer)
        navigationContainer.setNavigationItemSelectedListener(this)
        getUser()
        changeColorStatusBar(window, titleColor)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemHome -> {
                fragment.initData(item.itemId)
            }
            R.id.itemGrowVegetable -> {
                val fragmentManager = supportFragmentManager
                drawerLayout.closeDrawer(GravityCompat.START)
                DialogFragment().show(fragmentManager, null)
            }
            R.id.itemAlreadyHarvest -> {
                fragment.initData(item.itemId)
            }
            R.id.itemWormVegetable -> {
                fragment.initData(item.itemId)
            }
            R.id.itemLackedWater -> {
                fragment.initData(item.itemId)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    internal fun handleReplaceFragment(
        fragment: Fragment,
        backStack: Boolean = false,
        parent: Int,
        nameBackStack: String = ""
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(parent, fragment)
        if (backStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }

    private fun handleActionBarListener() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.action_bar_title)

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer_content_desc_rec,
            R.string.close_drawer_content_desc_rec
        ) {}
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    private fun getUser() {
        val header = navigationContainer.getHeaderView(0)
        val user = appDatabase?.getUserDao()?.getUsers()
        user?.apply {
            header.tvHomeUserName?.text = userName
            header.tvHomeHomeTown?.text = homeTown
            header.tvHomeUniversity?.text = university
            if (avatar == "") {
                header.imgHomeUser?.setImageResource(R.drawable.img_user)
            } else {
                header.imgHomeUser?.setImageURI(Uri.parse(avatar))
            }
        }
    }
}
