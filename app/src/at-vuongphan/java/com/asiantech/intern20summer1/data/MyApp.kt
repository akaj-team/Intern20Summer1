package com.asiantech.intern20summer1.data

import android.annotation.SuppressLint
import com.asiantech.intern20summer1.database.Cultivation
import com.asiantech.intern20summer1.database.Plant
import java.text.SimpleDateFormat
import java.util.*

class MyApp {
    companion object {
        internal const val FORMAT_CODE_DATE = "dd/MM/yyyy HH:mm"
        internal const val NAME_DIR = "imagePlants"
        internal const val FILE_TAIL = ".jpg"
        private const val MINUTES = 60
        private const val HOURS = 3600
        internal const val MODE_PLANTS =1
    }

    @SuppressLint("SimpleDateFormat")
    fun isPlantWormed(plant: Plant?, culti: Cultivation?): Boolean {
        culti?.dateWatering?.let { dateWatering ->
            val dateFormat = SimpleDateFormat(FORMAT_CODE_DATE)
            var beforeTime = 0
            dateFormat.parse(dateWatering)?.let { date ->
                Calendar.getInstance().apply {
                    time = date
                    beforeTime = getMinuteInDay(this)
                }
            }
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getMinuteInDay(now)
            plant?.wateringInterval?.let {
                return (current - beforeTime) / 2 >= (it * MINUTES)
            }
        }
        return false
    }
    @SuppressLint("SimpleDateFormat")
    fun getDateHarvest(cultivation: String?, plant: Plant): String {
        cultivation?.let { cul ->
            val dateFormat = SimpleDateFormat(FORMAT_CODE_DATE)
            val calendar = Calendar.getInstance()
            dateFormat.parse(cul)?.let { calendar.time = it }
            plant.growZoneNumber?.let { calendar.add(Calendar.MINUTE, it) }
            return dateFormat.format(calendar.time)
        }
        return "null"
    }

    @SuppressLint("SimpleDateFormat")
    fun isPlantLackWater(plant: Plant?, culti: Cultivation?): Boolean {
        culti?.dateWatering?.let { dateWatering ->
            val dateFormat = SimpleDateFormat(FORMAT_CODE_DATE)
            var beforeTime = 0
            dateFormat.parse(dateWatering)?.let { date ->
                Calendar.getInstance().apply {
                    time = date
                    beforeTime = getMinuteInDay(this)
                }
            }
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getMinuteInDay(now)
            plant?.wateringInterval?.let {
                return (current - beforeTime) >= (it * MINUTES)
            }
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun isPlantHarvest(plant: Plant?, culti: Cultivation?): Boolean {
        culti?.dateCultivation?.let { dateWatering ->
            val dateFormat = SimpleDateFormat(FORMAT_CODE_DATE)
            var beforeTime = 0
            dateFormat.parse(dateWatering)?.let { date ->
                Calendar.getInstance().apply {
                    time = date
                    beforeTime = getMinuteInDay(this)
                }
            }
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getMinuteInDay(now)
            plant?.growZoneNumber?.let {
                return (current - beforeTime) >= (it * MINUTES)
            }
        }
        return false
    }

    private fun getMinuteInDay(calendar: Calendar): Int {
        return calendar.get(Calendar.HOUR) * HOURS + calendar.get(Calendar.MINUTE) * MINUTES + calendar.get(
            Calendar.SECOND
        )
    }
}
