package dev.elysium.eraces.gui.raceSelect

import dev.elysium.eraces.gui.core.GuiBase
import dev.elysium.eraces.gui.core.GuiButton
import dev.elysium.eraces.gui.core.GuiManager
import dev.elysium.eraces.utils.ChatUtil
import org.bukkit.Material
import org.bukkit.entity.Player

class RaceSelectMenu(player: Player) : GuiBase(player, "Выбор расы") {

    private var currentIndex = 0

    init {
        preventClose = true
        closeMessage = "<red>Ты должен выбрать расу, прежде чем продолжить!"
    }


    override fun setup() {

        val pages = RaceSelectMenuPages.pages


        // Загружаем расы из races.d, если список пуст
        if (pages.isEmpty()) {
            RaceSelectMenuPages.loadFromConfig()
        }


        if (pages.isEmpty()) {
            ChatUtil.message(
                player,
                "<red>Ошибка: список рас пуст. Проверь races.yml и races.d!"
            )

            player.closeInventory()
            return
        }


        clearButtons()


        if (currentIndex < 0)
            currentIndex = 0

        if (currentIndex >= pages.size)
            currentIndex = pages.size - 1


        val race = pages[currentIndex]


        // Центральная кнопка с расой
        setButton(
            22,
            GuiButton(race.toItem()) {
            }
        )


        // Выбор расы
        setButton(
            31,
            GuiButton.of(
                Material.EMERALD_BLOCK,
                "<green>Выбрать расу"
            ) {

                preventClose = false

                GuiManager.close(player)
                player.closeInventory()

                RaceConfirmMenu(player, race).open()
            }
        )


        // Назад
        if (currentIndex > 0) {

            setButton(
                45,
                GuiButton.of(
                    Material.ARROW,
                    "<yellow>Предыдущая"
                ) {

                    currentIndex--
                    open()
                }
            )
        }


        // Вперёд
        if (currentIndex < pages.size - 1) {

            setButton(
                53,
                GuiButton.of(
                    Material.ARROW,
                    "<yellow>Следующая"
                ) {

                    currentIndex++
                    open()
                }
            )
        }
    }
}
