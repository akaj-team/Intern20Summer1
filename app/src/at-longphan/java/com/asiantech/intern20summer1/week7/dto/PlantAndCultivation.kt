package com.asiantech.intern20summer1.week7.dto

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.asiantech.intern20summer1.week7.model.PlantRecyclerViewItem
import com.asiantech.intern20summer1.week7.other.DATETIME_FORMAT
import java.text.SimpleDateFormat
import java.util.*

class PlantAndCultivation(
    internal val id: Int,
    internal val userGrowId: Int,
    internal val plantId: String,
    internal val dateCultivation: String,
    internal val dateWatering: String,
    internal val name: String,
    internal val description: String,
    internal val growZoneNumber: Int,
    internal val wateringInterval: Int,
    internal val imageUrl: String
) {
    @RequiresApi(Build.VERSION_CODES.O)
    internal fun getPlantRecyclerViewItem(): PlantRecyclerViewItem {

        val dateHarvest = getDateHarvest()
        val isWormed = checkWormed()
        return PlantRecyclerViewItem(
            this.id,
            this.name,
            this.dateCultivation,
            dateHarvest,
            this.imageUrl,
            isWormed
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

    @SuppressLint("SimpleDateFormat")
    private fun checkWormed(): Boolean {
        val dateFormat = SimpleDateFormat(DATETIME_FORMAT)

        val calendarWatering = Calendar.getInstance()
        dateFormat.parse(this.dateWatering)?.let { calendarWatering.time = it }
        calendarWatering.add(Calendar.MILLISECOND, this.wateringInterval * 86400000 / 4)

        val dateNow = Calendar.getInstance().time
        val dateWatering = calendarWatering.time

        val diff = dateNow.time - dateWatering.time

        return diff >= 0
    }
}
