package com.asiantech.intern20summer1.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.data.VegetableUser
import com.asiantech.intern20summer1.fragment.VegetableDialogFragment
import com.asiantech.intern20summer1.fragment.VegetableFragmentRecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-vuongphan`.w7_home_activity.*
import kotlinx.android.synthetic.`at-vuongphan`.w7_nav_header.view.*

class VegetableHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w7_home_activity)
        actionBar?.hide()
        setSupportActionBar(toolBar)
        navView?.setNavigationItemSelectedListener(this)
        initView()
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
                VegetableDialogFragment.newInstance().show(supportFragmentManager, "")
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
        val user = intent.getSerializableExtra("image") as? VegetableUser
        navView.getHeaderView(0).let { header ->
            user.let {
                header.tvName.text = it?.name
                header.tvNameUniversity.text = it?.university
                it?.avatarUri?.let {
                    //   header.imgAvatar2?.setImageBitmap()
                }
            }
            header.imgBack.setOnClickListener {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
    }
}
