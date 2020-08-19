package com.asiantech.intern20summer1.week7.other

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.asiantech.intern20summer1.week7.dto.PlantAndCultivation
import com.asiantech.intern20summer1.week7.model.PlantRecyclerViewItem
import java.text.SimpleDateFormat
import java.util.*

/**
 * get PlantRecyclerViewItem from List PlantAndCultivation
 */
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
internal fun List<PlantAndCultivation>.getAboutToHarvestPlants(
    item: PlantAndCultivation,
    output: MutableList<PlantRecyclerViewItem>
) {
    val dateFormat = SimpleDateFormat(DATETIME_FORMAT)

    val calendarHarvest = Calendar.getInstance()
    dateFormat.parse(item.dateCultivation)?.let { calendarHarvest.time = it }
    calendarHarvest.add(Calendar.MILLISECOND, item.growZoneNumber * 86400000 / 2)

    val dateNow = Calendar.getInstance().time
    val dateCultivation = calendarHarvest.time

    val diff = dateNow.time - dateCultivation.time

    if (diff >= 0) {
        output.add(item.getPlantRecyclerViewItem())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
internal fun List<PlantAndCultivation>.getWormedPlants(
    item: PlantAndCultivation,
    output: MutableList<PlantRecyclerViewItem>
) {
    val dateFormat = SimpleDateFormat(DATETIME_FORMAT)

    val calendarWatering = Calendar.getInstance()
    dateFormat.parse(item.dateWatering)?.let { calendarWatering.time = it }
    calendarWatering.add(Calendar.MILLISECOND, item.wateringInterval * 86400000 / 4)

    val dateNow = Calendar.getInstance().time
    val dateWatering = calendarWatering.time

    val diff = dateNow.time - dateWatering.time

    if (diff >= 0) {
        output.add(item.getPlantRecyclerViewItem())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
internal fun List<PlantAndCultivation>.getDehydratedPlants(
    item: PlantAndCultivation,
    output: MutableList<PlantRecyclerViewItem>
) {
    val dateFormat = SimpleDateFormat(DATETIME_FORMAT)

    val calendarWatering = Calendar.getInstance()
    dateFormat.parse(item.dateWatering)?.let { calendarWatering.time = it }
    calendarWatering.add(Calendar.MILLISECOND, item.wateringInterval * 86400000 / 2)

    val dateNow = Calendar.getInstance().time
    val dateWatering = calendarWatering.time

    val diff = dateNow.time - dateWatering.time

    if (diff >= 0) {
        output.add(item.getPlantRecyclerViewItem())
    }
}
