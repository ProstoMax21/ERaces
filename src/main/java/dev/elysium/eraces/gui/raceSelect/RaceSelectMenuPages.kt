package dev.elysium.eraces.gui.raceSelect

import dev.elysium.eraces.ERaces
import org.bukkit.Material

object RaceSelectMenuPages {

    val categories = mutableListOf<RaceCategory>()

    fun loadFromConfig() {

        categories.clear()

        val races = ERaces.getInstance()
            .context
            .racesConfigManager
            .races

        for ((id, race) in races) {

            if (id == "default")
                continue

            val categoryId = race.category.ifBlank { "other" }

            var category = categories.find { it.id == categoryId }

            if (category == null) {

                category = RaceCategory(
                    id = categoryId,
                    displayName = categoryId.replaceFirstChar {
                        it.uppercase()
                    }
                )

                categories += category
            }

            val gui = race.raceGuiConfig

            category.races += RacePage(
                id = id,

                displayName = gui.name
                    .replace("&", "§")
                    .ifEmpty { id },

                lore = gui.lore
                    .replace("\\n", "\n")
                    .split("\n")
                    .map { it.replace("&", "§") },

                material = try {
                    Material.valueOf(gui.icon.uppercase())
                } catch (_: Exception) {
                    Material.BOOK
                },

                title = "race.$id"
            )
        }

        // Красивый порядок категорий
        categories.sortBy {

            when (it.id.lowercase()) {
                "elf" -> 0
                "human" -> 1
                "orc" -> 2
                "furry" -> 3
                else -> 99
            }

        }

        // Сортировка рас внутри категории
        categories.forEach { category ->

            category.races.sortBy {

                when (it.id) {

                    // Эльфы
                    "asir" -> 1
                    "vaniar" -> 2
                    "sun_elf" -> 3
                    "elf" -> 4
                    "dark_elf" -> 5
                    "drow" -> 6
                    "ancient_elf" -> 7

                    else -> 999

                }

            }

        }

    }

    fun getCategory(index: Int): RaceCategory? {

        if (index < 0 || index >= categories.size)
            return null

        return categories[index]

    }

}
