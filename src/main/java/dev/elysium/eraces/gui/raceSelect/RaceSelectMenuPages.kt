package dev.elysium.eraces.gui.raceSelect

import dev.elysium.eraces.ERaces
import org.bukkit.Material

object RaceSelectMenuPages {

    val pages = mutableListOf<RaceCategory>()


    fun loadFromConfig() {

        pages.clear()


        val races = ERaces.getInstance()
            .context
            .racesConfigManager
            .races
            .filter { it.key != "default" }


        val grouped = races
            .groupBy {
                it.value.category ?: "other"
            }



        for ((categoryId, categoryRaces) in grouped) {


            val list = mutableListOf<RacePage>()


            val sorted = categoryRaces
                .toList()
                .sortedWith(
                    compareBy {

                        when(it.first) {

                            // Эльфы
                            "asir" -> 1
                            "vaniar" -> 2
                            "sun_elf" -> 3
                            "elf" -> 4
                            "dark_elf" -> 5
                            "drow" -> 6
                            "ancient_elf" -> 7


                            // Остальные
                            else -> 99
                        }

                    }
                )



            for ((id, race) in sorted) {


                val gui = race.raceGuiConfig


                list += RacePage(

                    id = id,


                    displayName = gui.name
                        .replace("&", "§")
                        .ifEmpty {
                            id
                        },


                    lore = gui.lore
                        .replace("\\n", "\n")
                        .split("\n")
                        .map {
                            it.replace("&", "§")
                        },


                    material = try {

                        Material.valueOf(
                            gui.icon.uppercase()
                        )

                    } catch (e: Exception) {

                        Material.BOOK

                    },


                    title = "race.$id"
                )
            }



            pages += RaceCategory(
                id = categoryId,
                name = when(categoryId) {

                    "elf" -> "Эльфы"
                    "human" -> "Люди"
                    "orc" -> "Орки"
                    "furry" -> "Зверолюди"

                    else -> categoryId
                },

                races = list
            )
        }
    }



    fun getById(id: String): RacePage? {

        return pages
            .flatMap {
                it.races
            }
            .find {
                it.id == id
            }
    }
}
