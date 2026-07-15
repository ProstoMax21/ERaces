package dev.elysium.eraces.gui.core

import org.bukkit.entity.Player
import java.util.UUID

/**
 * Отслеживает открытые меню игроков.
 */
object GuiManager {

    private val openMenus = mutableMapOf<UUID, GuiBase>()

    fun getOpenMenu(player: Player): GuiBase? {
        return openMenus[player.uniqueId]
    }

    fun setOpenMenu(player: Player, menu: GuiBase) {
        openMenus[player.uniqueId] = menu
    }

    fun close(player: Player) {
        openMenus.remove(player.uniqueId)
    }

    internal val allOpen: Map<UUID, GuiBase>
        get() = openMenus
}
