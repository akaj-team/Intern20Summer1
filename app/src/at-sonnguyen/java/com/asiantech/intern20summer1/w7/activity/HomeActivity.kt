package com.asiantech.intern20summer1.w7.activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.extension.replaceFragment
import com.asiantech.intern20summer1.w7.fragment.RegisterFragment.Companion.SHARED_PREFERENCE_AVATAR_KEY
import com.asiantech.intern20summer1.w7.fragment.RegisterFragment.Companion.SHARED_PREFERENCE_FILE
import com.asiantech.intern20summer1.w7.fragment.RegisterFragment.Companion.SHARED_PREFERENCE_UNIVERSITY_KEY
import com.asiantech.intern20summer1.w7.fragment.RegisterFragment.Companion.SHARED_PREFERENCE_USER_NAME_KEY
import com.asiantech.intern20summer1.w7.fragment.VegetableGardenFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-sonnguyen`.nav_header.view.*
import kotlinx.android.synthetic.`at-sonnguyen`.toolbar.*
import kotlinx.android.synthetic.`at-sonnguyen`.w7_activity_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w7_activity_home)
        supportActionBar?.hide();
        replaceFragment(R.id.flContent, VegetableGardenFragment())
        setSupportActionBar(toolbar);
        nvView?.setNavigationItemSelectedListener(this)
        initView()
        initNavigationHeaderData()
    }

    private fun initNavigationHeaderData() {
        val header = nvView.getHeaderView(0)
        val sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCE_FILE,Context.MODE_PRIVATE)
        header.tvName.text = sharedPreferences.getString(SHARED_PREFERENCE_USER_NAME_KEY,"Son Nguyen V.")
        header.tvUniversity.text = sharedPreferences.getString(SHARED_PREFERENCE_UNIVERSITY_KEY,"Da Nang University of Technology")
        if (sharedPreferences.getString(SHARED_PREFERENCE_AVATAR_KEY,"") ==""){
            header.imgAvatarHeader.setImageResource(R.drawable.ic_baseline_account_circle_24)
        }else {
            header.imgAvatarHeader.setImageURI(Uri.parse(sharedPreferences.getString(SHARED_PREFERENCE_AVATAR_KEY,"")))
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
//                openVegetableFragment(VegetableGardenFragment())
//                drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_second_fragment -> {
//                openVegetableFragment(FragmentTwo())
//                drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_fourth_fragment -> {
//                openVegetableFragment(FragmentThree())
//                drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_fifth_fragment -> {
//                val dialogFragment : DiaLogFragment1? = DiaLogFragment1.newInstance("Trong cay 1 ")
//                val fragmentManager : FragmentManager = supportFragmentManager
//                dialogFragment?.show(fragmentManager,"Trong cay 2")
//                drawer_layout.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }

    private fun openVegetableFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit()
    }
}
