package events

import bot.Bot
import dev.kord.core.event.guild.GuildCreateEvent

object GuildCreate {
    suspend fun execute(event: GuildCreateEvent, bot: Bot) {
        val guild = event.guild
        println("\u001B[36m[GuildCreate] Joined: ${guild.name} (${guild.id})\u001B[0m")
    }
}
