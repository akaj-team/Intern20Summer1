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
import com.asiantech.intern20summer1.w7.fragment.LackedWaterPlantFragment
import com.asiantech.intern20summer1.w7.fragment.PlantDialogFragment
import com.asiantech.intern20summer1.w7.fragment.VegetableGardenFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-sonnguyen`.nav_header.view.*
import kotlinx.android.synthetic.`at-sonnguyen`.toolbar.*
import kotlinx.android.synthetic.`at-sonnguyen`.w7_activity_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var database: PlantDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w7_activity_home)
        database = PlantDatabase.getInstance(applicationContext)
        supportActionBar?.hide();
        replaceFragment(R.id.flContent, VegetableGardenFragment.newInstance())
        setSupportActionBar(toolbar);
        nvView?.setNavigationItemSelectedListener(this)
        initView()
        initNavigationHeaderData()
    }

    private fun initNavigationHeaderData() {
        var user = database?.userDao()?.getAllUser()
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
                openVegetableFragment(VegetableGardenFragment())
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_second_fragment -> {
               PlantDialogFragment.newInstance().show(supportFragmentManager,"")
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_third_fragment ->{
                openVegetableFragment(LackedWaterPlantFragment.newInstance())
                drawer_layout.closeDrawer(GravityCompat.START)
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
}
