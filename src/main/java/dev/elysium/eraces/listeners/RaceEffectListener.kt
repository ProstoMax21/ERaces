package dev.elysium.eraces.listeners

import dev.elysium.eraces.ERaces
import dev.elysium.eraces.utils.EffectUtils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

object RaceEffectListener : Listener {

    private val plugin = ERaces.getInstance()


    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        applyRaceEffects(event.player)
    }


    @EventHandler
    fun onMove(event: PlayerMoveEvent) {

        // Не обновляем при повороте головы
        if (
            event.from.blockX == event.to.blockX &&
            event.from.blockY == event.to.blockY &&
            event.from.blockZ == event.to.blockZ
        ) return


        applyRaceEffects(event.player)
    }


    private fun applyRaceEffects(player: Player) {

        val raceId = plugin.context
            .playerDataManager
            .getPlayerRaceId(player)
            ?: return


        val race = plugin.context
            .racesConfigManager
            .races[raceId]
            ?: return


        val effects = race.effectsWith


        /*
         * Биомы
         */

        for (group in effects.effectsWithBiomes) {

            if (!group.biomes.contains(player.location.block.biome.key.key))
                continue


            for ((effect, level) in group.effects) {

                addEffect(
                    player,
                    effect,
                    level
                )
            }
        }



        /*
         * Свет
         */

        for (light in effects.effectsWithLights) {

            val block = player.location.block


            val lightLevel = when(light.lightType.lowercase()) {

                "sky" ->
                    block.lightFromSky

                "block" ->
                    block.lightFromBlocks

                else ->
                    block.lightLevel
            }


            if (lightLevel in light.minLight..light.maxLight) {

                for ((effect, level) in light.effects) {

                    addEffect(
                        player,
                        effect,
                        level
                    )
                }
            }
        }



        /*
         * Мир
         */

        for (world in effects.inWorld) {

            if (world.world != player.world.name)
                continue


            for ((effect, level) in world.effects) {

                addEffect(
                    player,
                    effect,
                    level
                )
            }
        }
    }



    private fun addEffect(
        player: Player,
        effect: String,
        amplifier: Int
    ) {

        try {

            val type = EffectUtils.getPotionEffectType(effect)

            player.addPotionEffect(
                org.bukkit.potion.PotionEffect(
                    type,
                    60,
                    amplifier - 1,
                    false,
                    false
                )
            )

        } catch (e: Exception) {

            plugin.logger.warning(
                "Не удалось применить эффект $effect: ${e.message}"
            )

        }
    }
}
