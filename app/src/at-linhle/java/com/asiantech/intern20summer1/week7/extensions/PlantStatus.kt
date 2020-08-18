package com.asiantech.intern20summer1.week7.extensions

import android.annotation.SuppressLint
import com.asiantech.intern20summer1.week7.fragments.DialogFragment.Companion.DATE_FORMAT_STRING
import com.asiantech.intern20summer1.week7.models.Cultivation
import com.asiantech.intern20summer1.week7.models.Plant
import java.text.SimpleDateFormat
import java.util.*

class PlantStatus {
    companion object {
        internal const val SECOND_IN_MINUTE = 60
        internal const val SECOND_IN_HOUR = 3600
        private const val DIVIDE_NUMBER = 4
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateHarvest(cultivation: String?, plant: Plant?): String {
        cultivation?.let { cul ->
            val dateFormat = SimpleDateFormat(DATE_FORMAT_STRING)
            val calendar = Calendar.getInstance()
            dateFormat.parse(cul)?.let { calendar.time = it }
            plant?.growZoneNumber?.let { calendar.add(Calendar.DATE, it) }
            return dateFormat.format(calendar.time)
        }
        return ""
    }

    @SuppressLint("SimpleDateFormat")
    fun isWormed(plant: Plant?, cultivation: Cultivation?): Boolean {
        cultivation?.dateWatering?.let { dateWatering ->
            val dateFormat = SimpleDateFormat(DATE_FORMAT_STRING)
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
                return (current - beforeTime) >= (it * SECOND_IN_MINUTE) / DIVIDE_NUMBER
            }
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun isLackedWater(plant: Plant?, cultivation: Cultivation?): Boolean {
        cultivation?.dateWatering?.let { dateWatering ->
            val dateFormat = SimpleDateFormat(DATE_FORMAT_STRING)
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
                return (current - beforeTime) >= (it * SECOND_IN_MINUTE) / 2
            }
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun isComingHarvest(plant: Plant?, cultivation: Cultivation?): Boolean {
        cultivation?.dateCultivation?.let { dateWatering ->
            val dateFormat = SimpleDateFormat(DATE_FORMAT_STRING)
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
                return (current - beforeTime) >= (it * SECOND_IN_MINUTE) / 2
            }
        }
        return false
    }

    private fun getSecondInDay(calendar: Calendar): Int {
        return calendar.get(Calendar.HOUR) * SECOND_IN_HOUR + calendar.get(Calendar.MINUTE) * SECOND_IN_MINUTE + calendar.get(
            Calendar.SECOND
        )
    }
}
