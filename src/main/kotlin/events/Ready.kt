package events

import bot.Bot
import dev.kord.core.event.gateway.ReadyEvent

object Ready {
    suspend fun execute(event: ReadyEvent, bot: Bot) {
        val self = bot.kord.getSelf()
        self.edit {
            presence {
                playing(bot.config.botName)
            }
        }

        println("\u001B[32m[Ready] Logged in as ${self.tag}\u001B[0m")
    }
}
