package com.asiantech.intern20summer1.week7

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-hoangtran`.activity_garden.*
import kotlinx.android.synthetic.`at-hoangtran`.navigation_header.view.*
import kotlinx.android.synthetic.`at-hoangtran`.toolbar.*

class GardenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var appDatabase: AppDatabase? = null
    private var fragment = GrowPlantFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garden)

        handleActionBar()
        handleReplaceFragment(fragment, parent = R.id.flToolbarContainer)
        nav_bar.setNavigationItemSelectedListener(this)
        getUser()
    }

    private fun handleActionBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = "Garden"

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
        when (item.itemId) {
            R.id.item_vuon_cay -> {
                fragment.initData(item.itemId)
            }
            R.id.item_trong_cay -> {
                val fragmentManager = supportFragmentManager
                dl_garden.closeDrawer(GravityCompat.START)
                PlantDialogFragment().show(fragmentManager, null)
            }
            R.id.item_cay_sap_thu_hoach -> {
                fragment.initData(item.itemId)
            }
            R.id.item_cay_bi_sau -> {
                fragment.initData(item.itemId)
            }
            R.id.item_cay_bi_thieu_nuoc -> {
                fragment.initData(item.itemId)
            }
        }
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

    private fun getUser() {
        val header = nav_bar.getHeaderView(0)
        val user = appDatabase?.getUserDAO()?.getUser()
        user?.apply{
            header.tvHomeUserName?.text = user.userName
            header.tvHomeUniversity?.text = user.university
            header.tvHomeHomeTown?.text = user.homeTown
            if (user.avatar == "") {
                header.imgHomeUser?.setImageResource(R.mipmap.ic_launcher_round)
            } else {
                header.imgHomeUser?.setImageURI(Uri.parse(user.avatar))
            }
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
}
