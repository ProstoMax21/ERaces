package dev.elysium.eraces.gui.raceSelect

import dev.elysium.eraces.ERaces
import org.bukkit.Material

object RaceSelectMenuPages {

    val pages = mutableListOf<RacePage>()

    fun loadFromConfig() {

        pages.clear()

        val races = ERaces.getInstance()
            .context
            .racesConfigManager
            .races


        for ((id, race) in races) {

            if (id == "default") continue

            pages += RacePage(
                id = id,
                displayName = race.raceGuiConfig.name ?: id,
                lore = race.raceGuiConfig.lore ?: emptyList(),
                material = Material.BOOK,
                title = "race.$id"
            )
        }
    }


    fun getById(id: String): RacePage? {
        return pages.find { it.id == id }
    }
}
