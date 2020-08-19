package com.asiantech.intern20summer1.extension.w7

import android.annotation.SuppressLint
import com.asiantech.intern20summer1.database.Cultivation
import com.asiantech.intern20summer1.database.Plant
import com.asiantech.intern20summer1.fragment.w7.VegetableDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class PlantStatus {
    companion object {
        private const val MINUTES = 60
        private const val HOURS = 3600
        private const val DIVIDE_NUMBER = 4
    }

    internal fun isPlantWorm(plant: Plant?, culti: Cultivation?): Boolean {
        culti?.dateWatering?.let { dateWatering ->
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getMinuteInDay(now)
            plant?.wateringInterval?.let {
                return (current - getDate(dateWatering)) >= (it * MINUTES) / DIVIDE_NUMBER
            }
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateHarvest(cultivation: String?, plant: Plant): String {
        cultivation?.let { cul ->
            val dateFormat = SimpleDateFormat(VegetableDialogFragment.FORMAT_CODE_DATE)
            val calendar = Calendar.getInstance()
            dateFormat.parse(cul)?.let { calendar.time = it }
            plant.growZoneNumber?.let { calendar.add(Calendar.MINUTE, it) }
            return dateFormat.format(calendar.time)
        }
        return "null"
    }

    fun isPlantLackWater(plant: Plant?, culti: Cultivation?): Boolean {
        culti?.dateWatering?.let { dateWatering ->
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getMinuteInDay(now)
            plant?.wateringInterval?.let {
                return (current - getDate(dateWatering)) >= (it * MINUTES) / 2
            }
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(dateWatering: String): Int {
        val dateFormat = SimpleDateFormat(VegetableDialogFragment.FORMAT_CODE_DATE)
        var beforeTime = 0
        dateFormat.parse(dateWatering)?.let {
            Calendar.getInstance().apply {
                time = it
                beforeTime = getMinuteInDay(this)
            }
        }
        return beforeTime
    }

    fun isPlantHarvest(plant: Plant?, culti: Cultivation?): Boolean {
        culti?.dateCultivation?.let { dateWatering ->
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getMinuteInDay(now)
            plant?.growZoneNumber?.let {
                return (current - getDate(dateWatering)) >= (it * MINUTES) / 2
            }
        }
        return false
    }

    private fun getMinuteInDay(calendar: Calendar): Int {
        return calendar.get(Calendar.HOUR) * HOURS + calendar.get(
            Calendar.MINUTE
        ) * MINUTES + calendar.get(
            Calendar.SECOND
        )
    }
}