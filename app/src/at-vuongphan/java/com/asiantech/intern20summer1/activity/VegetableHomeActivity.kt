package com.asiantech.intern20summer1.activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragment.VegetableDetailFragment
import com.asiantech.intern20summer1.fragment.VegetableDialogFragment
import com.asiantech.intern20summer1.fragment.VegetableFragmentRecyclerView
import com.asiantech.intern20summer1.fragment.VegetableRegisterFragment.Companion.SHARED_PREFERENCE_AVATAR_KEY
import com.asiantech.intern20summer1.fragment.VegetableRegisterFragment.Companion.SHARED_PREFERENCE_FILE
import com.asiantech.intern20summer1.fragment.VegetableRegisterFragment.Companion.SHARED_PREFERENCE_UNIVERSITY_KEY
import com.asiantech.intern20summer1.fragment.VegetableRegisterFragment.Companion.SHARED_PREFERENCE_USER_NAME_KEY
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
        getUser()
        initImageViewBack()
        openFragment(VegetableFragmentRecyclerView.newInstance())
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
                openFragment(VegetableDetailFragment.newInstance())
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.navNuoc -> {
                openFragment(VegetableFragmentRecyclerView.newInstance())
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.imgBack -> {
                Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun initView() {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolBar
            , R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun getUser() {
        val header = navView.getHeaderView(0)
        val sharedRef = this.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        header.tvName.text =
            sharedRef.getString(
                SHARED_PREFERENCE_USER_NAME_KEY,
                getString(R.string.text_view_name_description_text)
            )
        header.tvNameUniversity.text =
            sharedRef.getString(
                SHARED_PREFERENCE_UNIVERSITY_KEY,
                getString(R.string.text_view_university_description_text)
            )
        if (sharedRef.getString(
                SHARED_PREFERENCE_AVATAR_KEY,
                getString(R.string.share_preference_avatar_default)
            ) == ""
        ) {
            header.imgAvatar2.setImageResource(R.drawable.ic_splash)
        } else {
            header.imgAvatar2.setImageURI(
                Uri.parse(
                    sharedRef.getString(
                        SHARED_PREFERENCE_AVATAR_KEY,
                        getString(R.string.share_preference_avatar_default)
                    )
                )
            )
        }
    }

    private fun initImageViewBack() {
        navView.getHeaderView(0).let { header ->
            header.imgBack.setOnClickListener {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
    }

//    private fun openFragmentActivity(
//        fragment: Fragment,
//        backStack: Boolean = false,
//        nameBackStack: String = "null"
//    ) {
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frContainerDemo, fragment)
//        if (backStack) {
//            fragmentTransaction.addToBackStack(nameBackStack)
//        }
//        fragmentTransaction.commit()
//    }

    private fun openFragment(
        fragment: Fragment,
        backStack: Boolean = false,
        nameBackStack: String = "null"
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frContainerLayout, fragment)
        if (backStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }
}
