# Discord Handler Kotlin

A modern, feature-rich Discord bot handler built with **Kord**, featuring both slash commands and prefix commands with a robust modular architecture designed for scalability and maintainability.

## 🚀 Features

- **Dual Command System**: Support for both slash commands and prefix commands
- **Modular Architecture**: Clean separation of concerns with dedicated handlers
- **Anti-Crash System**: Comprehensive error handling and monitoring
- **Coroutine-Based**: Fully asynchronous with Kotlin coroutines
- **Webhook Logging**: Real-time logging for errors and guild events
- **MongoDB Integration**: Persistent data storage with mongodb-driver-kotlin-coroutine
- **Cooldown System**: Per-command cooldown management
- **Environment Configuration**: Secure configuration with dotenv-kotlin

## 📁 Project Structure

```
Discord-Handler-Kt/
├── build.gradle.kts              # Gradle build configuration
├── settings.gradle.kts           # Gradle settings
├── gradle.properties             # Gradle properties
├── src/                          # Source code
│   ├── Main.kt                   # Main bot entry point
│   ├── config/Config.kt          # Bot configuration from .env
│   ├── bot/Bot.kt                # Bot initialization
│   ├── Core/                     # Core utilities
│   │   ├── CommandUtils.kt       # Cooldown and utilities
│   │   ├── Emojis.kt             # Centralized emoji definitions
│   │   └── WebhookUtil.kt        # Webhook utility
│   ├── Database/
│   │   └── Mongo.kt              # MongoDB connection setup
│   ├── Events/                   # Discord event handlers
│   │   ├── GuildCreate.kt        # Handler when bot joins a server
│   │   ├── GuildDelete.kt        # Handler when bot leaves a server
│   │   ├── InteractionCreate.kt  # Handles slash command interactions
│   │   ├── MessageCreate.kt      # Handles prefix commands
│   │   └── Ready.kt              # Bot ready event
│   ├── Handlers/                 # Handlers for modularity
│   │   ├── AntiCrash.kt          # Crash prevention and error handling
│   │   └── Logger.kt             # Logger for bot activity
│   ├── Models/
│   │   └── UserModel.kt          # User data model
│   └── Commands/
│       ├── Prefix/               # Prefix commands
│       │   └── Ping.kt           # Example prefix ping command
│       └── Slash/                # Slash commands
│           └── Ping.kt           # Example slash ping command
```

## 🔧 Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/RealMtrx/Discord-Handler-Kt.git
   cd Discord-Handler-Kt
   ```

2. **Generate Gradle wrapper and build**

   ```bash
   gradle wrapper
   ./gradlew build
   ```

3. **Environment Setup**

   Copy `.env.example` to `.env` and fill in your values:

   ```env
   TOKEN=your_bot_token_here
   PREFIX=!
   BOT_NAME=Discord Handler
   MONGO_URI=mongodb://localhost:27017/discord-handler
   ERROR_WEBHOOK=https://discord.com/api/webhooks/your_webhook
   GUILD_LOG_WEBHOOK=https://discord.com/api/webhooks/your_webhook
   ```

4. **Run the bot**

   ```bash
   ./gradlew run
   ```

## 📋 Dependencies

- **Kord**: v0.18.1 - Kotlin-native Discord API wrapper
- **mongodb-driver-kotlin-coroutine**: v4.11 - MongoDB driver
- **dotenv-kotlin**: v6.5 - Environment variable management
- **ktor-client-cio**: v2.3 - HTTP client for webhooks

## 📝 Command Development

### Creating Slash Commands

Create a new file in `src/Commands/Slash/[name].kt`:

```kotlin
import com.kord.core.*
import dev.kord.common.entity.SlashCommand
import dev.kord.core.event.InteractionCreateEvent

object Ping : SlashCommand("ping", "Replies with Pong!") {
    override suspend fun execute(event: InteractionCreateEvent) {
        event.interaction.respond { content = "Pong! 🏓" }
    }
}
```

### Creating Prefix Commands

Create a new file in `src/Commands/Prefix/[name].kt`:

```kotlin
import dev.kord.core.entity.Message

object Ping {
    const val name = "ping"

    suspend fun execute(message: Message, args: List<String>) {
        message.channel.createMessage("Pong! 🏓")
    }
}
```

---

**Discord Handler** — Built by **Mtrx** — Discord: **0hu2**
