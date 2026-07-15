package dev.elysium.eraces.listeners

import dev.elysium.eraces.ERaces
import dev.elysium.eraces.RacesReloader
import dev.elysium.eraces.VisualsManager
import dev.elysium.eraces.gui.core.GuiManager
import dev.elysium.eraces.gui.raceSelect.RaceSelectMenu
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerJoinEvent

object RaceSelectListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {

        val player = event.player

        ERaces.getInstance().server.scheduler.runTaskLater(
            ERaces.getInstance(),
            Runnable {

                if (!player.isOnline) return@Runnable

                val raceId = ERaces.getInstance()
                    .context
                    .playerDataManager
                    .getPlayerRaceId(player)

                if (raceId.isNullOrEmpty() || raceId == "default") {

                    RaceSelectMenu(player).open()

                } else {

                    RacesReloader.reloadRaceForPlayer(player)
                    VisualsManager.updateVisualsForPlayer(player)

                }

            },
            20L
        )
    }


    @EventHandler
    fun onPlayerDamage(event: EntityDamageEvent) {

        val entity = event.entity

        if (entity is Player) {

            val menu = GuiManager.getOpenMenu(entity)

            if (menu?.preventClose == true) {
                event.isCancelled = true
            }
        }
    }
}
