package events

import bot.Bot
import commands.slash.Ping
import core.Emojis
import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent

object InteractionCreate {
    suspend fun execute(event: ChatInputCommandInteractionCreateEvent, bot: Bot) {
        val interaction = event.interaction
        val commandName = interaction.commandName

        try {
            when (commandName) {
                Ping.name -> Ping.execute(event)
                else -> {
                    interaction.respondEphemeral {
                        content = "${Emojis.error} Unknown command."
                    }
                }
            }
        } catch (e: Exception) {
            println("\u001B[31m[InteractionCreate] Error in /$commandName: ${e.message}\u001B[0m")
            if (!interaction.acknowledged) {
                interaction.respondEphemeral {
                    content = "${Emojis.error} Error executing command!"
                }
            }
        }
    }
}
