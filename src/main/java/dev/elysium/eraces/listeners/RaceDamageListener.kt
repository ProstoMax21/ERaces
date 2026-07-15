package dev.elysium.eraces.listeners

import dev.elysium.eraces.ERaces
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

object RaceDamageListener : Listener {


    private fun getRace(player: Player): String? {
        return ERaces.getInstance()
            .context
            .playerDataManager
            .getPlayerRaceId(player)
    }


    /*
     * Минусы от лечения
     * Тёмные эльфы получают урон от магического лечения
     */
    @EventHandler
    fun onRegainHealth(event: EntityRegainHealthEvent) {

        val player = event.entity as? Player ?: return

        val race = getRace(player) ?: return


        if (
            race == "dark_elf" ||
            race == "drow" ||
            race == "dark_elf_king"
        ) {

            when(event.regainReason) {

                EntityRegainHealthEvent.RegainReason.MAGIC,
                EntityRegainHealthEvent.RegainReason.MAGIC_REGEN -> {

                    player.damage(event.amount)

                }

                else -> {}
            }
        }
    }



    /*
     * Урон от солнца для тёмных эльфов
     */
    @EventHandler
    fun onSunDamage(event: EntityDamageEvent) {

        val player = event.entity as? Player ?: return

        val race = getRace(player) ?: return


        if (
            race == "dark_elf" ||
            race == "drow"
        ) {

            if (
                event.cause ==
                EntityDamageEvent.DamageCause.FIRE
            ) {

                if (player.world.environment ==
                    org.bukkit.World.Environment.NORMAL &&
                    player.location.block.lightFromSky >= 15
                ) {

                    event.damage *= 1.5

                }
            }
        }
    }



    /*
     * Дополнительные эффекты при атаке
     */
    @EventHandler
    fun onAttack(event: EntityDamageByEntityEvent) {

        val attacker = event.damager as? Player ?: return

        val race = getRace(attacker) ?: return


        if (race == "drow") {

            // шанс наложить яд
            if (Math.random() < 0.15) {

                val victim = event.entity

                if (victim is Player) {

                    victim.addPotionEffect(
                        org.bukkit.potion.PotionEffect(
                            org.bukkit.potion.PotionEffectType.POISON,
                            60,
                            0
                        )
                    )

                }
            }
        }
    }
}
