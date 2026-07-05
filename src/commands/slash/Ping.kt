package commands.slash

import core.CommandUtils
import core.Emojis
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.behavior.reply
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent

object Ping {
    val name = "ping"
    val description = "Replies with Pong!"
    val cooldownMs = 3000L

    suspend fun execute(event: ChatInputCommandInteractionCreateEvent) {
        val interaction = event.interaction

        val cooldown = CommandUtils.checkCooldown(
            interaction.user.id.toString(), name, cooldownMs
        )
        if (cooldown.onCooldown) {
            interaction.respondEphemeral {
                content = "${Emojis.loading} Please wait ${cooldown.timeLeft} seconds before using this command again."
            }
            return
        }

        interaction.respondEphemeral {
            content = "${Emojis.success} Pong!"
        }
    }
}
