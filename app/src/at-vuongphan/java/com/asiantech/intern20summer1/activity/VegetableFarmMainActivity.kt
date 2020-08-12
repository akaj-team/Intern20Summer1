package com.asiantech.intern20summer1.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.database.VegetableDB
import com.asiantech.intern20summer1.fragment.VegetableDialogFragment
import com.asiantech.intern20summer1.fragment.VegetableFragmentRecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.`at-vuongphan`.w7_activity_main_farm.*
import kotlinx.android.synthetic.`at-vuongphan`.w7_nav_header.view.*

class VegetableFarmMainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private var dataBase: VegetableDB? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w7_activity_main_farm)
        handleReplaceFragment(
            VegetableFragmentRecyclerView.newInstance(),
            parent = R.id.frContainerLayout
        )
        dataBase = VegetableDB.dataBaseConnect(this)
        initView()
        navigationView.setNavigationItemSelectedListener(this)
        initUser()
        initImageViewBack()
    }

    private fun initUser() {
        val user = dataBase?.userDao()?.getUser()
        navigationView.getHeaderView(0)?.let { hd ->
            user?.let { user ->
                hd.tvName.text = user.userName
                hd.tvNameUniversity.text = user.university
                user.imgUri?.let { imgUri ->
                    hd.imgAvatar2?.setImageURI(Uri.parse(imgUri))
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initImageViewBack() {
        navigationView.getHeaderView(0).let { header ->
            header.imgBack.setOnClickListener {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun initView() {
        setSupportActionBar(toolbarHome)
        val actionBar = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
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
                Toast.makeText(this, "aaaa", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.navNuoc -> {
                Toast.makeText(this, "aaaa", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }
}
