package com.asiantech.intern20summer1.week7.dto

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.asiantech.intern20summer1.week7.model.PlantRecyclerViewItem
import com.asiantech.intern20summer1.week7.other.DATETIME_FORMAT
import java.text.SimpleDateFormat
import java.util.*

class PlantAndCultivation(
    private val id: Int,
    private val userGrowId: Int,
    private val plantId: String,
    private val dateCultivation: String,
    private val dateWatering: String,
    private val name: String,
    private val description: String,
    private val growZoneNumber: Int,
    private val wateringInterval: Int,
    private val imageUrl: String
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getPlantRecyclerViewItem(): PlantRecyclerViewItem {

        val dateHarvest = getDateHarvest()

        return PlantRecyclerViewItem(
            this.id,
            this.name,
            this.dateCultivation,
            dateHarvest,
            this.imageUrl,
            false
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateHarvest(): String {
        val dateFormat = SimpleDateFormat(DATETIME_FORMAT)
        val calendar = Calendar.getInstance()
        dateFormat.parse(dateCultivation)?.let { calendar.time = it }
        calendar.add(Calendar.DATE, growZoneNumber)
        return dateFormat.format(calendar.time)
    }
}
