# Discord Handler Kt

A modern, feature-rich Discord bot handler built with Kotlin and Kord, featuring both slash commands and prefix commands with a robust modular architecture.

## Features

- Slash commands and prefix commands
- MongoDB integration with coroutine support
- Modular architecture (commands, events, handlers)
- Anti-crash system with error reporting
- Cooldown system
- Unicode emoji exports
- Webhook logging

## Prerequisites

- Java 21+
- Gradle 8.x (or use the Gradle wrapper)

## Setup

1. Clone the repository
2. Copy `.env.example` to `.env` and fill in your bot token and other configuration
3. Run the bot:

```bash
./gradlew run
```

## Project Structure

```
src/main/kotlin/
├── Main.kt              # Entry point
├── bot/
│   └── Bot.kt           # Bot initialization and event registration
├── config/
│   └── Config.kt        # Configuration loader
├── commands/
│   ├── slash/
│   │   └── Ping.kt      # Slash ping command
│   └── prefix/
│       └── Ping.kt      # Prefix ping command
├── core/
│   ├── CommandUtils.kt  # Cooldown utilities
│   ├── Emojis.kt        # Unicode emoji exports
│   └── WebhookUtil.kt   # Webhook utility
├── database/
│   └── Mongo.kt         # MongoDB connection
├── events/
│   ├── Ready.kt         # Ready event
│   ├── GuildCreate.kt   # Guild join event
│   ├── GuildDelete.kt   # Guild leave event
│   ├── InteractionCreate.kt # Slash command handler
│   └── MessageCreate.kt # Prefix command handler
├── handlers/
│   ├── AntiCrash.kt     # Error handling
│   └── Logger.kt        # Startup logger
└── models/
    └── UserModel.kt     # User data model
```

## Adding Commands

### Slash Command

Create a new file in `commands/slash/` following the pattern in `Ping.kt`, then register it in `Bot.kt`'s `registerSlashCommands()` method and add the handler in `events/InteractionCreate.kt`.

### Prefix Command

Create a new file in `commands/prefix/` following the pattern in `Ping.kt`, then add it to the `prefixCommands` map in `events/MessageCreate.kt`.

## License

MIT License - see [LICENSE](LICENSE) for details.
