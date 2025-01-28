package QuokkaGame.com.github.changeJoinMessagePlugin

import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.user.User
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.CommandExecutor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerQuitEvent


class ChangeJoinMessagePlugin : JavaPlugin(), Listener {

    private lateinit var luckPerms: LuckPerms

    override fun onEnable() {
        // Plugin startup logic
        CommandAPI.onEnable()
        logger.info("ChangeJoinMessagePlugin has been enabled.")

        reigisterCommands()
        saveDefaultConfig()
        server.pluginManager.registerEvents(this, this)

        try {
            luckPerms = LuckPermsProvider.get()
        } catch (e: Exception) {
            logger.severe("§cFailed to get LuckPerms API. Please make sure LuckPerms is installed correctly.")
            e.printStackTrace()
            server.pluginManager.disablePlugin(this)
            return
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
        CommandAPI.onDisable()
        logger.info("ChangeJoinMessagePlugin has been disabled.")
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (config.getBoolean("join-message-toggle", true)) {
            sendCustomMessage(event.player, "join-message", "joined the game")
        }
        event.joinMessage(null)
    }

    @EventHandler
    fun onPlayerLeft(event: PlayerQuitEvent) {
        if (config.getBoolean("leave-message-toggle", true)) {
            sendCustomMessage(event.player, "leave-message", "left the game")
        }
        event.quitMessage(null)
    }

    private fun sendCustomMessage(player: Player, configKey: String, defaultMessage: String) {
        val user: User = luckPerms.userManager.getUser(player.uniqueId)!!
        val prefix: String = user.cachedData.metaData.prefix.orEmpty()
        val suffix: String = user.cachedData.metaData.suffix.orEmpty()
        val displayName =LegacyComponentSerializer.legacySection().serialize(player.displayName())
        val formattedMessage = config.getString(configKey, "§e{prefix}{name}{suffix} $defaultMessage")
            ?.replace("{prefix}", prefix)
            ?.replace("{suffix}", suffix)
            ?.replace("{name}", player.name)
            ?.replace("{displayname}", displayName)
            .orEmpty()

        server.consoleSender.sendMessage(formattedMessage)
        server.onlinePlayers.filter { it.uniqueId != player.uniqueId }.forEach { it.sendMessage(formattedMessage) }
    }

    private fun reigisterCommands() {
        CommandAPICommand("changejoinmessage")
            .withPermission("changejoinmessage.command.use")
            .withAliases("cjm")
            .withUsage("/changejoinmessage <reload>")
            .withShortDescription("ChangeJoinMessage command")
            .executes(CommandExecutor { sender, args ->
                sender.sendMessage("§3Usage: /changejoinmessage <reload>")
            })
            .withSubcommand(
                CommandAPICommand("reload")
                    .withPermission("changejoinmessage.command.use.reload")
                    .withShortDescription("reload ChangeJoinMessage config")
                    .executes(CommandExecutor { sender, args ->
                        reloadConfig()
                        sender.sendMessage("§aChangeJoinPlugin config reloaded!")
                    })
            )
            .register()
    }
}
