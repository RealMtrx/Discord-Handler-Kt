package events

import bot.Bot
import commands.prefix.Ping
import core.Emojis
import dev.kord.core.event.message.MessageCreateEvent

object MessageCreate {
    private val prefixCommands = mapOf(
        Ping.name to Ping,
        "p" to Ping
    )

    suspend fun execute(event: MessageCreateEvent, bot: Bot) {
        val message = event.message
        val author = message.author ?: return
        if (author.isBot || author.id == bot.kord.selfId) return

        val content = message.content
        val prefix = bot.config.prefix
        if (!content.startsWith(prefix)) return

        val args = content.removePrefix(prefix).trim().split(Regex("\\s+"))
        val commandName = args.firstOrNull()?.lowercase() ?: return
        val commandArgs = args.drop(1)

        val command = prefixCommands[commandName] ?: return

        try {
            command.execute(message, commandArgs)
        } catch (e: Exception) {
            println("\u001B[31m[Prefix] Error in $commandName: ${e.message}\u001B[0m")
        }
    }
}
