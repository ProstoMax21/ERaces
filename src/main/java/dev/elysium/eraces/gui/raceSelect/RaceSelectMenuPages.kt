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

            val gui = race.raceGuiConfig

            pages += RacePage(
                id = id,
                displayName = gui.name.ifEmpty { id },
                lore = gui.lore
                    .split("\\n")
                    .map { it.replace("&", "§") },
                material = try {
                    Material.valueOf(gui.icon.uppercase())
                } catch (e: Exception) {
                    Material.BOOK
                },
                title = "race.$id"
            )
        }
    }


    fun getById(id: String): RacePage? {
        return pages.find { it.id == id }
    }
}
