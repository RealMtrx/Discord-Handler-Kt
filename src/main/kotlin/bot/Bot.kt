package bot

import commands.slash.Ping
import config.Config
import database.Mongo
import dev.kord.core.Kord
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.guild.GuildCreateEvent
import dev.kord.core.event.guild.GuildDeleteEvent
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import events.*
import handlers.AntiCrash
import handlers.Logger
import handlers.StartupData

class Bot(val config: Config) {
    lateinit var kord: Kord
        private set

    suspend fun start() {
        println("\u001B[36m\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557\u001B[0m")
        println("\u001B[36m\u2551     Starting Discord Handler     \u2551\u001B[0m")
        println("\u001B[36m\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D\u001B[0m")
        println()

        println("\u001B[34m[System] Initializing AntiCrash...\u001B[0m")
        AntiCrash.init(config)

        println("\u001B[34m[System] Connecting to MongoDB...\u001B[0m")
        val mongoConnected = Mongo.connect(config)

        println("\u001B[34m[System] Connecting to Discord...\u001B[0m")
        kord = Kord(config.token)

        registerEvents()
        registerSlashCommands()

        Logger.startupReport(
            StartupData(
                name = config.botName,
                prefix = 1,
                slash = 1,
                events = 5,
                anticrash = true,
                mongo = mongoConnected
            )
        )

        kord.login()
    }

    private fun registerEvents() {
        kord.on<ReadyEvent> {
            Ready.execute(this, this@Bot)
        }

        kord.on<GuildCreateEvent> {
            GuildCreate.execute(this, this@Bot)
        }

        kord.on<GuildDeleteEvent> {
            GuildDelete.execute(this, this@Bot)
        }

        kord.on<ChatInputCommandInteractionCreateEvent> {
            InteractionCreate.execute(this, this@Bot)
        }

        kord.on<MessageCreateEvent> {
            events.MessageCreate.execute(this, this@Bot)
        }

        println("\u001B[32m[System] Events registered (5)\u001B[0m")
    }

    private suspend fun registerSlashCommands() {
        try {
            kord.createGlobalChatInputCommand(Ping.name, Ping.description)
            println("\u001B[32m[System] Slash commands registered (1)\u001B[0m")
        } catch (e: Exception) {
            println("\u001B[31m[System] Failed to register slash commands: ${e.message}\u001B[0m")
            AntiCrash.reportError("Slash Command Registration", e.message ?: "Unknown error")
        }
    }
}
