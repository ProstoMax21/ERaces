package dev.elysium.eraces.gui.raceSelect

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

        meta.setDisplayName(displayName)

        meta.lore = lore

        item.itemMeta = meta

        return item
    }
}
