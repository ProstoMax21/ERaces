package dev.elysium.eraces.gui.raceSelect

import dev.elysium.eraces.utils.ChatUtil
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class RacePage(
    val id: String,
    val displayName: String,
    val lore: List<String>,
    val material: Material,
    val title: String
) {

    fun toItem(): ItemStack {

        val item = ItemStack(material)

        val meta = item.itemMeta ?: return item

        meta.displayName(
            ChatUtil.parse(displayName)
        )

        meta.lore(
            lore.map {
                ChatUtil.parse(it)
            }
        )

        item.itemMeta = meta

        return item
    }
}
