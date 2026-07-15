package dev.elysium.eraces.gui.raceSelect

import dev.elysium.eraces.gui.core.GuiBase
import dev.elysium.eraces.gui.core.GuiButton
import dev.elysium.eraces.utils.ChatUtil
import org.bukkit.Material
import org.bukkit.entity.Player


class RaceSelectMenu(player: Player) : GuiBase(player, "Выбор расы") {


    private var currentCategory = 0


    init {
        preventClose = true
        closeMessage = "<red>Ты должен выбрать расу, прежде чем продолжить!"
    }



    override fun setup() {


        val categories = RaceSelectMenuPages.categories


        if (categories.isEmpty()) {
            RaceSelectMenuPages.loadFromConfig()
        }


        if (categories.isEmpty()) {

            ChatUtil.message(
                player,
                "<red>Ошибка: список категорий рас пуст!"
            )

            player.closeInventory()
            return
        }


        clearButtons()



        if (currentCategory >= categories.size)
            currentCategory = categories.size - 1


        val category = categories[currentCategory]



        // Название категории

        setButton(
            4,
            GuiButton.of(
                Material.NETHER_STAR,
                "<gold>${category.displayName}"
            ) {}
        )



        /*
         * Расы в один ряд
         *
         * Слоты:
         * 10 11 12 13 14 15 16
         */

        var slot = 10


        for (race in category.races) {


            if (slot > 16)
                break


            setButton(
                slot,
                GuiButton(race.toItem()) {

                    RaceConfirmMenu(
                        player,
                        race
                    ).open()

                }
            )


            slot++

        }



        // Назад

        if (currentCategory > 0) {

            setButton(
                45,
                GuiButton.of(
                    Material.ARROW,
                    "<yellow>Предыдущая категория"
                ) {

                    currentCategory--
                    open()

                }
            )

        }



        // Вперёд

        if (currentCategory < categories.size - 1) {

            setButton(
                53,
                GuiButton.of(
                    Material.ARROW,
                    "<yellow>Следующая категория"
                ) {

                    currentCategory++
                    open()

                }
            )

        }

    }

}
