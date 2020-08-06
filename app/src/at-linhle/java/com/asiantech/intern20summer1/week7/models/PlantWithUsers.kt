package com.asiantech.intern20summer1.week7.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PlantWithUsers(
    @Embedded val plant: Plant,
    @Relation(
        parentColumn = "id",
        entity = User::class,
        entityColumn = "userId",
        associateBy = Junction(
            Cultivation::class,
            parentColumn = "plantId",
            entityColumn = "userGrowId"
        )
    )
    val users: List<User>
)
