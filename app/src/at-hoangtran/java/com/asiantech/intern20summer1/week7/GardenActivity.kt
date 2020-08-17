package com.asiantech.intern20summer1.week7

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-hoangtran`.activity_garden.*
import kotlinx.android.synthetic.`at-hoangtran`.navigation_header.view.*
import kotlinx.android.synthetic.`at-hoangtran`.toolbar.*

class GardenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var plantList: MutableList<Plant?>
    private lateinit var plantStorage: MutableList<Plant?>
    private lateinit var adapter: GardenViewHolder
    private var appDatabase: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garden)

        handleActionBar()
        nav_bar.setNavigationItemSelectedListener(this)
        initData()
        getUser()
        initAdapter()
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
        if (item.itemId == R.id.item_trong_cay) {
            val fragmentManager = supportFragmentManager
            dl_garden.closeDrawer(GravityCompat.START)
            DialogFragment().show(fragmentManager, null)
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
        var user = appDatabase?.getUserDAO()?.getUser()
        user?.apply {
            header.tvHomeUserName?.text = userName
            header.tvHomeUniversity?.text = university
            header.tvHomeHomeTown?.text = homeTown
            if (avatar == "") {
                header.imgHomeUser?.setImageResource(R.mipmap.ic_launcher_round)
            } else {
                header.imgHomeUser?.setImageURI(Uri.parse(avatar))
            }
        }
    }

    private fun initAdapter() {
        plantStorage.shuffle()
        plantList = plantStorage
        adapter = GardenViewHolder(recyclerVieGarden, this, plantList)
        recyclerVieGarden?.adapter = adapter
    }

    private fun initData() {
        plantStorage = mutableListOf()
    }
}
