package dev.elysium.eraces.listeners

import dev.elysium.eraces.ERaces
import dev.elysium.eraces.gui.core.GuiManager
import dev.elysium.eraces.utils.actionMsg
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerQuitEvent


/**
 * Слушатель событий для GUI-меню.
 */
object GuiListener : Listener {


    lateinit var plugin: ERaces


    fun init(plugin: ERaces) {
        this.plugin = plugin
    }



    /**
     * Обрабатывает клики по GUI.
     */
    @EventHandler(priority = EventPriority.HIGH)
    fun onInventoryClick(event: InventoryClickEvent) {


        val player = event.whoClicked as? Player
            ?: return


        val menu = GuiManager.getOpenMenu(player)
            ?: return



        // Проверяем именно верхний инвентарь GUI
        if (event.view.topInventory != menu.inv) {
            return
        }



        event.isCancelled = true


        menu.handleClick(event)

    }




    /**
     * Обрабатывает закрытие GUI.
     */
    @EventHandler(priority = EventPriority.HIGH)
    fun onInventoryClose(event: InventoryCloseEvent) {


        val player = event.player as? Player
            ?: return


        val menu = GuiManager.getOpenMenu(player)
            ?: return



        // Если меню было открыто заново
        if (menu.isReopened) {

            menu.isReopened = false
            return

        }



        // Запрет закрытия
        if (menu.preventClose) {


            menu.closeMessage?.let {

                player.actionMsg(it)

            }



            player.server.scheduler.runTaskLater(
                plugin,
                Runnable {

                    menu.open(true)

                },
                1L
            )



        } else {


            GuiManager.close(player)

        }

    }





    /**
     * Очистка после выхода игрока.
     */
    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerLeave(event: PlayerQuitEvent) {


        GuiManager.close(event.player)

    }

}
