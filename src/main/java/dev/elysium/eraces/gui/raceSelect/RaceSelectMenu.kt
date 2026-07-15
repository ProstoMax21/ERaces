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


        val categories = RaceSelectMenuPages.pages



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



        if (currentCategory < 0)
            currentCategory = 0



        if (currentCategory >= categories.size)
            currentCategory = categories.size - 1



        val category = categories[currentCategory]



        // Заголовок категории

        setButton(
            4,
            GuiButton.of(
                Material.NETHER_STAR,
                "<gold>${category.name}"
            ) {}
        )



        // Расстановка рас

        var slot = 10


        for (race in category.races) {


            while (
                slot == 17 ||
                slot == 26 ||
                slot == 35
            ) {
                slot++
            }



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




        // Предыдущая категория

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




        // Следующая категория

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
