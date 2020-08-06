package com.asiantech.intern20summer1.w7.main.recyclerview

data class RecyclerItem(
    val id: Int? = null,
    val userGrowId: Int? = null,
    val planId: Int? = null,
    val name: String = "",
    val dateCultivation: String = "",
    val dateHarvest: String = "",
    val statusWorm: Boolean = false,
    val image: Int = 0
)
