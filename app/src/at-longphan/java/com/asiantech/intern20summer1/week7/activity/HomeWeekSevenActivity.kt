package com.asiantech.intern20summer1.week7.activity

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.PlantRoomDatabase
import com.asiantech.intern20summer1.week7.entity.Plant
import com.asiantech.intern20summer1.week7.fragment.GardenFragment
import com.asiantech.intern20summer1.week7.fragment.PlantNewDialogFragment
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.`at-longphan`.activity_home_w7.*
import kotlinx.android.synthetic.`at-longphan`.navigation_header_w7.view.*
import java.util.concurrent.Executors

class HomeWeekSevenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toggle: ActionBarDrawerToggle
    private val fragmentManager = supportFragmentManager
    private var database: PlantRoomDatabase? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_w7)
        configStatusBar()
        navigationViewWeek7?.setNavigationItemSelectedListener(this)

        database = PlantRoomDatabase.getDatabase(this)
        saveDataFromJsonFile(this)

        initToolbar()
        initToggleDrawer()
        initHeaderView()
        addPlantRecyclerViewFragment()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment
        when (item.itemId) {
            R.id.navGarden -> {
                Toast.makeText(
                    this,
                    fragmentManager.backStackEntryCount.toString(),
                    Toast.LENGTH_LONG
                ).show()
                fragment = GardenFragment()
                fragmentManager.beginTransaction().replace(R.id.navHostFragment, fragment).commit()
            }
            R.id.navPlantNew -> {
                fragment = PlantNewDialogFragment()
                fragmentManager.beginTransaction().add(R.id.navHostFragment, fragment)
                    .addToBackStack(null).commit()
            }
            R.id.navAboutToHarvest -> {
                Toast.makeText(this, "Cây sắp thu hoạch", Toast.LENGTH_SHORT).show()
            }
            R.id.navWormedPlant -> {
                Toast.makeText(this, "Cây bị sâu", Toast.LENGTH_SHORT).show()
            }
            R.id.navDehydratedPlant -> {
                Toast.makeText(this, "Cây bị thiếu nước", Toast.LENGTH_SHORT).show()
            }
        }
        toolbarTitle.text = item.title
        drawerLayoutWeek7?.closeDrawers()
        return true
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun initToolbar() {
        /**
         * This will use default toolbar with a ham and a title
         */
        //setSupportActionBar(toolbarWeeK7)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //listener for hamburger
        toolbarHamburger?.setOnClickListener {
            drawerLayoutWeek7?.openDrawer(Gravity.LEFT)
        }
    }

    private fun initToggleDrawer() {
        toggle = ActionBarDrawerToggle(
            this, drawerLayoutWeek7, null,
            R.string.text_description_open_drawer, R.string.text_description_close_drawer
        )
        drawerLayoutWeek7?.addDrawerListener(toggle)
    }

    private fun addPlantRecyclerViewFragment() {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(
            R.id.navHostFragment,
            GardenFragment()
        )
        transaction.commit()
    }

    private fun initHeaderView() {
        val sharePref: SharedPreferences =
            this.getSharedPreferences("UserDataPrefs", Context.MODE_PRIVATE)

        navigationViewWeek7?.getHeaderView(0)?.let {
            it.tvUserNameWeek7?.text = sharePref.getString("userName", null)
            it.tvUniversityWeek7?.text = sharePref.getString("university", null)
            val avatarUrl = sharePref.getString("avatarUrl", null)
            if (avatarUrl != null) {
                it.imgAvatarHeaderWeek7?.setImageURI(Uri.parse(avatarUrl))
            }

            it.imgBackHeader?.setOnClickListener {
                drawerLayoutWeek7?.closeDrawers()
            }
        }
    }

    private fun saveDataFromJsonFile(context: Context) {
        Executors.newFixedThreadPool(2).execute {
            context.assets.open("plants.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plants: List<Plant> = Gson().fromJson(jsonReader, plantType)
                    database?.plantDao()?.insertList(plants)
                }
            }
        }
    }
}
