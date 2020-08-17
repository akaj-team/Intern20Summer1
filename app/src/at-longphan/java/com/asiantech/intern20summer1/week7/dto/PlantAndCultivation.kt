package com.asiantech.intern20summer1.week7.dto

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.asiantech.intern20summer1.week7.model.PlantRecyclerViewItem
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
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getPlantRecyclerViewItem(): PlantRecyclerViewItem{
        // Find dateHarvest
        /*val date = this.dateCultivation
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH'h'mm")
        val dateHarvest = ZonedDateTime.parse(date, formatter).plusDays(this.growZoneNumber.toLong()).toString()*/

        val dateFormat = SimpleDateFormat("dd/MM/yy HH'h'mm")
        val calendar = Calendar.getInstance()
        dateFormat.parse(dateCultivation)?.let{calendar.time = it}
        calendar.add(Calendar.DATE, growZoneNumber)
        val dateHarvest = dateFormat.format(calendar.time)

        return PlantRecyclerViewItem(
            this.id,
            this.name,
            this.dateCultivation,
            dateHarvest,
            this.imageUrl,
            false
        )
    }
}
