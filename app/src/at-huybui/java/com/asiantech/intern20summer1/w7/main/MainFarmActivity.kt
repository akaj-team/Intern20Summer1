package com.asiantech.intern20summer1.w7.main

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.main.fragment.DialogFragmentFarm
import com.asiantech.intern20summer1.w7.main.fragment.TreeRecyclerFragment
import kotlinx.android.synthetic.`at-huybui`.activity_main_farm.*
import kotlinx.android.synthetic.`at-huybui`.navigation_header.view.*


class MainFarmActivity : AppCompatActivity() {

    private var dataBase: ConnectDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_farm)
        dataBase = ConnectDataBase.dataBaseConnect(applicationContext)
        handleReplaceFragment(TreeRecyclerFragment.newInstance(), parent = R.id.containerMain)
        initToolBar()
        initNavigationDrawer()
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

    private fun initToolBar() {
        setSupportActionBar(toolbarHome)
        val actionBar = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.icon_menu_drawer)

        }
    }

    private fun initNavigationDrawer() {
        val user = dataBase?.userDao()?.getUser()
        navigationView.getHeaderView(0)?.let { hd ->
            user?.let { u ->
                hd.tvNameHeader.text = u.userName
                hd.tvUniversityHeader?.text = getString(R.string.w7_header_university, u.university)
                hd.tvHomeTownHeader?.text = getString(R.string.w7_header_home_town, u.homeTown)
                u.imgUri?.let { imgUri ->
                    hd.imgAvatarHeader?.setImageURI(Uri.parse(imgUri))
                }
            }
            hd.imgIconBack.setOnClickListener {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        navigationView?.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemGrowVegetable -> {
                    handleShowDialogFragment()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun handleShowDialogFragment() {
        val fragmentManager = supportFragmentManager
        val fragment = DialogFragmentFarm.newInstance()
        drawerLayout.closeDrawer(GravityCompat.START)
        fragment.show(fragmentManager, null)
    }
}
