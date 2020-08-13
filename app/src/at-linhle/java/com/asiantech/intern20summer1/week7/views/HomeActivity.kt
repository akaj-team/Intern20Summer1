package com.asiantech.intern20summer1.week7.views

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.adapters.VegetableViewHolder
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.fragments.DialogFragment
import com.asiantech.intern20summer1.week7.models.Plant
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-linhle`.activity_home_toolbar.*
import kotlinx.android.synthetic.`at-linhle`.coordinator_layout.*
import kotlinx.android.synthetic.`at-linhle`.navigation_view_header.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var plantItemList: MutableList<Plant?>
    private lateinit var plantItemsStorage: MutableList<Plant?>
    private lateinit var adapter: VegetableViewHolder
    private var appDatabase: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_toolbar)
        appDatabase = AppDatabase.getInstance(this)
        handleActionBarListener()
        navigationContainer.setNavigationItemSelectedListener(this)
        getUser()
        initData()
        initAdapter()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemGrowVegetable) {
            val fragmentManager = supportFragmentManager
            drawerLayout.closeDrawer(GravityCompat.START)
            DialogFragment().show(fragmentManager, null)
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

    private fun initData() {
        plantItemsStorage = mutableListOf()
    }

    private fun initAdapter() {
        plantItemsStorage.shuffle()
        plantItemList = plantItemsStorage
        adapter = VegetableViewHolder(recyclerViewContainer, this, plantItemList)
        recyclerViewContainer?.adapter = adapter
    }
}
