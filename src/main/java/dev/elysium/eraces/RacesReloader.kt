package dev.elysium.eraces

import dev.elysium.eraces.ERaces.Companion.getInstance
import dev.elysium.eraces.items.RaceChangePotion
import dev.elysium.eraces.listeners.*
import dev.elysium.eraces.listeners.PluginMessageListener.Companion.sendAbilities
import dev.elysium.eraces.updaters.UpdatersReloader.disableUpdatersForPlayers
import dev.elysium.eraces.updaters.UpdatersReloader.registerUpdatersListeners
import dev.elysium.eraces.updaters.UpdatersReloader.reloadUpdatersForPlayer
import dev.elysium.eraces.updaters.UpdatersReloader.unloadPlayerDataFromUpdaters
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

object RacesReloader : Listener {

    private val listeners: List<Listener> = listOf(
        PlayerJoinListener(),
        PlayerQuitListener(),
        PlayerRespawnListener(),
        RaceChangeGuiListener(),
        RaceChangePotion(),
        PlayerShootBowEventListener(),
    )

    fun reloadRaceForPlayer(player: Player) {

        val raceId = getInstance()
            .context
            .playerDataManager
            .getPlayerRaceId(player)

        if (raceId.isNullOrEmpty() || raceId == "default") {
            return
        }

        val race = getInstance()
            .context
            .racesConfigManager
            .races[raceId]
            ?: return

        reloadUpdatersForPlayer(player, race)
        sendAbilities(player)
    }


    fun reloadRaceForAllPlayers() {
        for (player in Bukkit.getOnlinePlayers()) {
            reloadRaceForPlayer(player)
        }
    }


    fun startListeners(plugin: JavaPlugin) {

        registerUpdatersListeners(plugin)

        for (listener in listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin)
        }
    }


    fun onPlayerLeave(player: Player) {
        unloadPlayerDataFromUpdaters(player)
        disableUpdatersForPlayers(player)
    }


    fun disableUpdaters() {

        for (player in Bukkit.getOnlinePlayers()) {
            disableUpdatersForPlayers(player)
        }
    }
}
