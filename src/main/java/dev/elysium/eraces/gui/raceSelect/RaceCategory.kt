package dev.elysium.eraces.gui.raceSelect

data class RaceCategory(
    val id: String,
    val name: String,
    val races: MutableList<RacePage>
)
