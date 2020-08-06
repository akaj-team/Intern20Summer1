package com.asiantech.intern20summer1.week7.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithPlants(
    @Embedded val user: User,
    @Relation(
        parentColumn = "useId",
        entity = Plant::class,
        entityColumn = "id",
        associateBy = Junction(
            Cultivation::class,
            parentColumn = "userGrowId",
            entityColumn = "plantId"
        )
    )
    val plants: List<Plant>
)
