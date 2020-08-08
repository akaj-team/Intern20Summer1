package com.asiantech.intern20summer1.week7.model

class Cultivation {

    internal var id: Int? = null
    internal var userGrew: Int? = null
    internal var plantId: Int? = null
    internal var dateCultivation: String? = null
    internal var dateWatering: String? = null

    constructor(
        id: Int,
        userGrew: Int,
        plantId: Int,
        dateCultivation: String,
        dateWatering: String
    ) {
        this.id = id
        this.userGrew = userGrew
        this.plantId = plantId
        this.dateCultivation = dateCultivation
        this.dateWatering = dateWatering
    }
}
