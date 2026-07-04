package commands.prefix

import core.CommandUtils
import core.Emojis
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message

object Ping {
    val name = "ping"
    val description = "Replies with Pong!"
    val aliases = listOf("p")
    val cooldownMs = 3000L

    suspend fun execute(message: Message, args: List<String>) {
        val cooldown = CommandUtils.checkCooldown(
            message.author?.id.toString(), name, cooldownMs
        )
        if (cooldown.onCooldown) {
            message.reply("${Emojis.loading} Please wait ${cooldown.timeLeft} seconds before using this command again.")
            return
        }

        message.reply("${Emojis.success} Pong!")
    }
}
