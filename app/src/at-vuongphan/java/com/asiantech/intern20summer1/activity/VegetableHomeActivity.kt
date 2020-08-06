package com.asiantech.intern20summer1.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragment.VegetableFragmentRecyclerView
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.`at-vuongphan`.w7_home_activity.*

class VegetableHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var tvNameHeader: TextView
    private lateinit var tvUniversityHeader: TextView
    private lateinit var imgAvatarHeader: CircleImageView
    private lateinit var imgBackHeader: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w7_home_activity)
        actionBar?.hide()
        setSupportActionBar(toolBar)
        navView?.setNavigationItemSelectedListener(this)
        initView()
        initViewHeader()
        initImageViewBack()
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
            R.id.navFarmer -> {
                // W7FragmentDialog.newInstance().show(supportFragmentManager, "Vuon rau")
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
            R.id.imgBack -> {
                Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun openFragmentRecyclerView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frContainerLayout, VegetableFragmentRecyclerView.newInstance()).commit()
    }

    private fun initView() {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolBar
            , R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initImageViewBack() {
        imgBackHeader.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun initViewHeader() {
        val header = navView.getHeaderView(0)
        imgBackHeader = header.findViewById(R.id.imgBack)
        tvNameHeader = header.findViewById(R.id.tvName)
        tvUniversityHeader = header.findViewById(R.id.tvNameUniversity)
        imgAvatarHeader = header.findViewById(R.id.imgAvatar2)
    }
}
