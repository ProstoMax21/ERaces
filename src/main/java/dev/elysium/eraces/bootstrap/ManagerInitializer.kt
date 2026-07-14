package dev.elysium.eraces.bootstrap

import dev.elysium.eraces.DamageTracker
import dev.elysium.eraces.ERaces
import dev.elysium.eraces.RacesReloader
import dev.elysium.eraces.abilities.AbilsManager
import dev.elysium.eraces.config.*
import dev.elysium.eraces.exceptions.internal.InitFailedException
import dev.elysium.eraces.gui.raceSelect.RaceSelectMenuPages
import dev.elysium.eraces.xpManager.XpManager
import org.bukkit.Bukkit

class ManagerInitializer : IInitializer {

    override fun setup(plugin: ERaces) {
        try {
            val ctx = plugin.context

            // Перезагрузка конфигов
            RacesReloader.startListeners(plugin)

            // Глобальный конфиг
            ctx.globalConfigManager = GlobalConfigManager(plugin)

            // Загрузка рас из races.yml и races.d
            ctx.racesConfigManager = RacesConfigManager(plugin)

            // Данные игроков
            ctx.playerDataManager = PlayerDataManager(
                ctx.racesConfigManager.races,
                ctx.database
            )

            // Создание списка рас для GUI
            RaceSelectMenuPages.loadFromConfig()

            // Специализации
            ctx.specializationsManager = SpecializationsManager(
                plugin,
                ctx.database
            )

            // Опыт и урон
            val xpManager = XpManager()
            Bukkit.getPluginManager().registerEvents(xpManager, plugin)
            ctx.xpManager = xpManager

            val damageTracker = DamageTracker()
            Bukkit.getPluginManager().registerEvents(damageTracker, plugin)
            ctx.xpDamageTracker = damageTracker

            // Мана
            ctx.manaManager = ManaManager(plugin)

            // Способности
            AbilsManager.init(plugin)

        } catch (e: Exception) {
            throw InitFailedException(
                "Ошибка при инициализации менеджеров",
                e
            )
        }
    }
}
