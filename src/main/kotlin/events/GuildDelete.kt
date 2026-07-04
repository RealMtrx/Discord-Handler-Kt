package events

import bot.Bot
import dev.kord.core.event.guild.GuildDeleteEvent

object GuildDelete {
    suspend fun execute(event: GuildDeleteEvent, bot: Bot) {
        val guildId = event.guildId
        println("\u001B[33m[GuildDelete] Left: $guildId\u001B[0m")
    }
}
