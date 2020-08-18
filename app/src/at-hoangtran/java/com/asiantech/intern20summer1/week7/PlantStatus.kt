package com.asiantech.intern20summer1.week7

import android.annotation.SuppressLint
import com.asiantech.intern20summer1.week7.PlantDialogFragment.Companion.DATE_FORMAT_STRING
import java.text.SimpleDateFormat
import java.util.*

class PlantStatus {
    companion object {
        internal const val SECOND_IN_1_MINUTE = 60
        internal const val SECOND_IN_1_HOUR = 3600
    }

    @SuppressLint("SimpleDateFormat")
    fun getHarvestDate(cultivation: String?, plant: Plant?): String {
        cultivation?.let {
            val dateFormat = SimpleDateFormat()
            val calendar = Calendar.getInstance()
            dateFormat.parse(cultivation)?.let { calendar.time = it }
            plant?.growZoneNumber?.let { calendar.add(Calendar.DATE, it) }
            return dateFormat.format(calendar.time)
        }
        return ""
    }

    @SuppressLint("SimpleDateFormat")
    fun isWormed(plant: Plant?, cultivation: Cultivation?): Boolean {
        cultivation?.dateWatering?.let { dateWatering ->
            val dateFormat = SimpleDateFormat()
            var beforeTime = 0
            dateFormat.parse(dateWatering)?.let { date ->
                Calendar.getInstance().apply {
                    time = date
                    beforeTime = getSecond(this)
                }
            }
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getSecond(now)
            plant?.wateringInterval?.let {
                return (current - beforeTime) >= (it * SECOND_IN_1_MINUTE) / 4
            }
        }
        return true
    }

    @SuppressLint("SimpleDateFormat")
    fun isWatered(plant: Plant?, cultivation: Cultivation?): Boolean {
        cultivation?.dateWatering?.let { dateWatering ->
            val dateFormat = SimpleDateFormat()
            var beforeTime = 0
            dateFormat.parse(dateWatering)?.let { date ->
                Calendar.getInstance().apply {
                    time = date
                    beforeTime = getSecond(this)
                }
            }
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getSecond(now)
            plant?.wateringInterval?.let {
                return (current - beforeTime) >= (it * SECOND_IN_1_MINUTE) / 2
            }
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun isHarvest(plant: Plant?, cultivation: Cultivation?): Boolean {
        cultivation?.dateCultivation?.let { dateWatering ->
            val dateFormat = SimpleDateFormat(DATE_FORMAT_STRING)
            var beforeTime = 0
            dateFormat.parse(dateWatering)?.let { date ->
                Calendar.getInstance().apply {
                    time = date
                    beforeTime = getSecond(this)
                }
            }
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getSecond(now)
            plant?.growZoneNumber?.let {
                return (current - beforeTime) >= (it * SECOND_IN_1_MINUTE) / 2
            }
        }
        return false
    }

    private fun getSecond(calendar: Calendar): Int {
        return calendar.get(Calendar.HOUR) * SECOND_IN_1_HOUR + calendar.get(Calendar.MINUTE) * SECOND_IN_1_MINUTE + calendar.get(
            Calendar.SECOND
        )
    }
}
