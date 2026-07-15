package dev.elysium.eraces.gui.raceSelect

data class RaceCategory(

    val id: String,

    val displayName: String,

    val races: MutableList<RacePage> = mutableListOf()

)
