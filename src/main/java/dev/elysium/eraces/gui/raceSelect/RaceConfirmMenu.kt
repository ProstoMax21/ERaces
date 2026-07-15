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


        setButton(
            11,
            GuiButton.of(
                Material.LIME_WOOL,
                "<green>Да, выбрать <gold>${race.name}"
            ) {


                val plugin = ERaces.getInstance()


                player.actionMsg(
                    "<green>Ты выбрал расу: <gold>${race.name}"
                )


                plugin.context
                    .playerDataManager
                    .setPlayerRaceAsync(
                        player,
                        race.id
                    )
                    .thenRun {


                        Bukkit.getScheduler().runTask(
                            plugin,
                            Runnable {

                                RacesReloader.reloadRaceForPlayer(player)

                                VisualsManager.updateVisualsForPlayer(player)

                                GuiManager.close(player)

                                player.closeInventory()

                            }
                        )

                    }

            }
        )


        setButton(
            15,
            GuiButton.of(
                Material.RED_WOOL,
                "<red>Нет, вернуться"
            ) {


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

                val plugin = ERaces.getInstance()


                player.actionMsg(
                    "<green>Ты выбрал расу: <gold>${race.displayName}"
                )


                // Сохраняем расу

                plugin.context
                    .playerDataManager
                    .setPlayerRaceAsync(
                        player,
                        race.id
                    )
                    .thenRun {


                        Bukkit.getScheduler().runTask(
                            plugin,
                            Runnable {


                                RacesReloader.reloadRaceForPlayer(player)

                                VisualsManager.updateVisualsForPlayer(player)


                                GuiManager.close(player)

                                player.closeInventory()


                            }
                        )

                    }

            }
        )



        // Отмена

        setButton(
            15,
            GuiButton.of(
                Material.RED_WOOL,
                "<red>Нет, вернуться"
            ) {


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
