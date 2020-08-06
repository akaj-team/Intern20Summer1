package com.asiantech.intern20summer1.week7.views

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.adapters.VegetableViewHolder
import com.asiantech.intern20summer1.week7.fragments.RegisterFragment.Companion.SHARED_PREFERENCE_AVATAR_KEY
import com.asiantech.intern20summer1.week7.fragments.RegisterFragment.Companion.SHARED_PREFERENCE_FILE
import com.asiantech.intern20summer1.week7.fragments.RegisterFragment.Companion.SHARED_PREFERENCE_HOME_TOWN_KEY
import com.asiantech.intern20summer1.week7.fragments.RegisterFragment.Companion.SHARED_PREFERENCE_UNIVERSITY_KEY
import com.asiantech.intern20summer1.week7.fragments.RegisterFragment.Companion.SHARED_PREFERENCE_USER_NAME_KEY
import com.asiantech.intern20summer1.week7.models.Plant
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-linhle`.activity_home_toolbar.*
import kotlinx.android.synthetic.`at-linhle`.coordinator_layout.*
import kotlinx.android.synthetic.`at-linhle`.navigation_view_header.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var plantItemList: MutableList<Plant?>
    private lateinit var plantItemsStorage: MutableList<Plant?>
    private lateinit var adapter: VegetableViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_toolbar)
        handleActionBarListener()
        navigationContainer.setNavigationItemSelectedListener(this)
        getUser()
        initData()
        initAdapter()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
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
        val sharedRef = this.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        header.tvHomeUserName.text =
            sharedRef.getString(
                SHARED_PREFERENCE_USER_NAME_KEY,
                getString(R.string.share_preference_user_name_default)
            )
        header.tvHomeHomeTown.text =
            sharedRef.getString(
                SHARED_PREFERENCE_HOME_TOWN_KEY,
                getString(R.string.share_preference_home_town_default)
            )
        header.tvHomeUniversity.text =
            sharedRef.getString(
                SHARED_PREFERENCE_UNIVERSITY_KEY,
                getString(R.string.share_preference_university_default)
            )
        if (sharedRef.getString(
                SHARED_PREFERENCE_AVATAR_KEY,
                getString(R.string.share_preference_avatar_default)
            ) == ""
        ) {
            header.imgHomeUser.setImageResource(R.drawable.img_user)
        } else {
            header.imgHomeUser.setImageURI(
                Uri.parse(
                    sharedRef.getString(
                        SHARED_PREFERENCE_AVATAR_KEY,
                        getString(R.string.share_preference_avatar_default)
                    )
                )
            )
        }
    }

    private fun initData() {
        plantItemsStorage = mutableListOf()

    }

    private fun initAdapter() {
        plantItemsStorage.shuffle()
        plantItemList = plantItemsStorage
        adapter = VegetableViewHolder(recyclerViewContainer, this, plantItemList)
        recyclerViewContainer.adapter = adapter
    }
}
