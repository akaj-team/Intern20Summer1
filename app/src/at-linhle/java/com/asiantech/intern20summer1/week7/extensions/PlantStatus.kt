package com.asiantech.intern20summer1.week7.extensions

import android.annotation.SuppressLint
import com.asiantech.intern20summer1.week7.fragments.DialogFragment
import com.asiantech.intern20summer1.week7.fragments.DialogFragment.Companion.DATE_FORMAT_STRING
import com.asiantech.intern20summer1.week7.models.Plant
import java.text.SimpleDateFormat
import java.util.*

class PlantStatus {
    @SuppressLint("SimpleDateFormat")
    fun getDateHarvest(cultivation: String?, plant: Plant): String {
        cultivation?.let { cul ->
            val dateFormat = SimpleDateFormat(DATE_FORMAT_STRING)
            val calendar = Calendar.getInstance()
            dateFormat.parse(cul)?.let { calendar.time = it }
            plant.growZoneNumber?.let { calendar.add(Calendar.DATE, it) }
            return dateFormat.format(calendar.time)
        }
        return ""
    }
}
