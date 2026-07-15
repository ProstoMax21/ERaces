package dev.elysium.eraces.gui.raceSelect

import dev.elysium.eraces.ERaces
import dev.elysium.eraces.RacesReloader
import dev.elysium.eraces.VisualsManager
import dev.elysium.eraces.gui.core.GuiBase
import dev.elysium.eraces.gui.core.GuiButton
import dev.elysium.eraces.gui.core.GuiManager
import dev.elysium.eraces.utils.actionMsg
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class RaceConfirmMenu(
    player: Player,
    private val race: RacePage
) : GuiBase(player, "Подтверждение выбора", 27) {

    init {
        preventClose = true
        closeMessage = "<red>Ты должен выбрать расу, прежде чем продолжить!"
    }


    override fun setup() {


        // Да, выбрать расу
        setButton(
            11,
            GuiButton.of(
                Material.LIME_WOOL,
                "<green>Да, выбрать ${race.displayName}"
            ) {

                preventClose = false

                GuiManager.close(player)

                player.actionMsg(
                    "<green>Ты выбрал расу: <gold>${race.displayName}"
                )


                ERaces.getInstance()
                    .context
                    .playerDataManager
                    .setPlayerRaceAsync(player, race.id)
                    .thenRun {

                        Bukkit.getScheduler().runTask(
                            ERaces.getInstance(),
                            Runnable {

                                RacesReloader.reloadRaceForPlayer(player)

                                VisualsManager.updateVisualsForPlayer(player)

                                player.closeInventory()

                            }
                        )
                    }
            }
        )


        // Нет, назад
        setButton(
            15,
            GuiButton.of(
                Material.RED_WOOL,
                "<red>Нет, вернуться"
            ) {

                preventClose = false

                GuiManager.close(player)


                Bukkit.getScheduler().runTaskLater(
                    ERaces.getInstance(),
                    Runnable {

                        RaceSelectMenu(player).open()

                    },
                    1L
                )
            }
        )
    }
}
