package com.asiantech.intern20summer1.w7.extension

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.data.Cultivation
import com.asiantech.intern20summer1.w7.database.data.DownloadPlantImage
import com.asiantech.intern20summer1.w7.database.data.DownloadPlantImage.Companion.SECOND_IN_HOUR
import com.asiantech.intern20summer1.w7.database.data.DownloadPlantImage.Companion.SECOND_IN_MINUTE
import com.asiantech.intern20summer1.w7.database.data.Plant
import java.text.SimpleDateFormat
import java.util.*

internal fun AppCompatActivity.replaceFragment(
    containerId: Int,
    fragment: Fragment,
    isAddToBackStack: Boolean = false
): Fragment? {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(containerId, fragment, fragment.javaClass.simpleName)
    if (isAddToBackStack) {
        transaction.addToBackStack(fragment.javaClass.simpleName)
    }
    transaction.commit()
    return fragment
}

@SuppressLint("SimpleDateFormat")
fun isWormed(plant: Plant?, cultivation: Cultivation?): Boolean {
    cultivation?.dateWatering?.let { dateWatering ->
        val dateFormat = SimpleDateFormat(DownloadPlantImage.FORMAT_CODE_DATE)
        var beforeTime = 0
        dateFormat.parse(dateWatering)?.let { date ->
            Calendar.getInstance().apply {
                time = date
                beforeTime = getSecondInDay(this)
            }
        }
        val now = Calendar.getInstance()
        now.time = Date()
        val current = getSecondInDay(now)
        plant?.wateringInterval?.let {
            return (current - beforeTime) >= (it * SECOND_IN_MINUTE)/4
        }
    }
    return false
}

@SuppressLint("SimpleDateFormat")
fun isLackedWater(plant: Plant?, cultivation: Cultivation?): Boolean {
    cultivation?.dateWatering?.let { dateWatering ->
        val dateFormat = SimpleDateFormat(DownloadPlantImage.FORMAT_CODE_DATE)
        var beforeTime = 0
        dateFormat.parse(dateWatering)?.let { date ->
            Calendar.getInstance().apply {
                time = date
                beforeTime = getSecondInDay(this)
            }
        }
        val now = Calendar.getInstance()
        now.time = Date()
        val current = getSecondInDay(now)
        plant?.wateringInterval?.let {
            return (current - beforeTime) >= (it * SECOND_IN_MINUTE)/2
        }
    }
    return false
}

@SuppressLint("SimpleDateFormat")
fun isComingHarvest(plant: Plant?, cultivation: Cultivation?): Boolean {
    cultivation?.dateCultivation?.let { dateWatering ->
        val dateFormat = SimpleDateFormat(DownloadPlantImage.FORMAT_CODE_DATE)
        var beforeTime = 0
        dateFormat.parse(dateWatering)?.let { date ->
            Calendar.getInstance().apply {
                time = date
                beforeTime = getSecondInDay(this)
            }
        }
        val now = Calendar.getInstance()
        now.time = Date()
        val current = getSecondInDay(now)
        plant?.growZoneNumber?.let {
            return (current - beforeTime) >= (it * SECOND_IN_MINUTE)/2
        }
    }
    return false
}

internal fun getSecondInDay(calendar: Calendar): Int {
    return calendar.get(Calendar.HOUR) * SECOND_IN_HOUR + calendar.get(Calendar.MINUTE) * SECOND_IN_MINUTE + calendar.get(
        Calendar.SECOND
    )
}

internal fun getDateHarvest(cultivationDate: String?, plant: Plant): String {
    cultivationDate?.let { cul ->
        val dateFormat = SimpleDateFormat(DownloadPlantImage.FORMAT_CODE_DATE)
        val calendar = Calendar.getInstance()
        dateFormat.parse(cul)?.let { calendar.time = it }
        plant.growZoneNumber?.let { calendar.add(Calendar.MINUTE, it) }
        return dateFormat.format(calendar.time)
    }
    return ""
}
internal fun AppCompatActivity.openVegetableFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit()
}