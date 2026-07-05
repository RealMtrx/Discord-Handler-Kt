<div align="center">
  <h1>Discord Handler — Kotlin</h1>
  <p><strong>A production-ready Discord bot framework built with Kord and MongoDB — slash commands, prefix commands, anti-crash, webhook logging, coroutine-based async, and a modular src/ architecture.</strong></p>

  <p>
    <a href="https://github.com/RealMtrx/Discord-Handler-Kt/blob/main/LICENSE"><img src="https://img.shields.io/badge/license-MIT-blue.svg" alt="License"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Kt/releases"><img src="https://img.shields.io/badge/version-0.9.0--beta-yellow" alt="Version 0.9.0 Beta"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Kt/stargazers"><img src="https://img.shields.io/github/stars/RealMtrx/Discord-Handler-Kt" alt="Stars"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Kt/issues"><img src="https://img.shields.io/github/issues/RealMtrx/Discord-Handler-Kt" alt="Issues"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Kt/network"><img src="https://img.shields.io/github/forks/RealMtrx/Discord-Handler-Kt" alt="Forks"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler/graphs/contributors"><img src="https://img.shields.io/badge/ecosystem-26%20repos-brightgreen" alt="26 Repos"></a>
    <a href="https://discord.gg/0hu2"><img src="https://img.shields.io/badge/discord-0hu2-5865F2" alt="Discord"></a>
  </p>

  <br>

  <p>
    <a href="#-features">Features</a> •
    <a href="#-quick-start">Quick Start</a> •
    <a href="#-project-structure">Structure</a> •
    <a href="#-api-reference">API</a> •
    <a href="#-database-edition">SQL Edition</a> •
    <a href="#-related-repositories">Ecosystem</a>
  </p>
</div>

---

## Overview

Discord Handler Kotlin is a production-ready bot framework using **Kord 0.18.1** with Kotlin 2.0.21 on JVM 21. Built with Kotlin coroutines for fully asynchronous operations, it provides a dual command system (slash + prefix), anti-crash protection, rich webhook logging, MongoDB persistence via the coroutine driver, and per-command cooldowns.

## Features

- **Dual Command System** — Slash and prefix commands with automatic registration
- **Coroutine-Based** — Fully asynchronous with `kotlinx-coroutines-core` 1.9.0
- **Anti-Crash System** — Global error handling and monitoring
- **Webhook Logging** — Real-time webhook notifications for errors and guild events
- **MongoDB Integration** — Coroutine-native MongoDB driver (`mongodb-driver-kotlin-coroutine` 5.1.4)
- **Cooldown System** — Per-command cooldown management
- **Environment Configuration** — `dotenv-kotlin` 6.4.1 for secure config
- **Logback Logging** — Structured logging via `logback-classic` 1.5.13

## Quick Start

```bash
# Clone the repository
git clone https://github.com/RealMtrx/Discord-Handler-Kt.git
cd Discord-Handler-Kt

# Generate Gradle wrapper and build
gradle wrapper
./gradlew build

# Configure environment
cp .env.example .env
# Edit .env with your bot token, MongoDB URI, and webhook URLs

# Run the bot
./gradlew run
```

### Environment Variables

```env
TOKEN=your_bot_token
PREFIX=!
BOT_NAME=Discord Handler
MONGO_URI=mongodb://localhost:27017/discord-handler
ERROR_WEBHOOK=https://discord.com/api/webhooks/...
GUILD_LOG_WEBHOOK=https://discord.com/api/webhooks/...
```

## Project Structure

```
Discord-Handler-Kt/
├── build.gradle.kts                 # Gradle build (Kotlin 2.0.21, Kord 0.18.1)
├── settings.gradle.kts              # Gradle settings
├── gradle.properties                # Gradle properties
├── src/
│   ├── Main.kt                      # Entry point — starts bot with coroutines
│   ├── config/Config.kt             # .env configuration parser
│   ├── bot/Bot.kt                   # Kord client builder & command maps
│   ├── Core/
│   │   ├── CommandUtils.kt          # Cooldown map & utility extensions
│   │   ├── Emojis.kt                # Centralized emoji constants
│   │   └── WebhookUtil.kt           # Webhook HTTP sender
│   ├── Database/
│   │   └── Mongo.kt                 # MongoDB coroutine client setup
│   ├── Events/
│   │   ├── GuildCreate.kt           # onGuildJoin
│   │   ├── GuildDelete.kt           # onGuildLeave
│   │   ├── InteractionCreate.kt     # Slash command dispatcher
│   │   ├── MessageCreate.kt         # Prefix command dispatcher
│   │   └── Ready.kt                 # onReady
│   ├── Handlers/
│   │   ├── AntiCrash.kt             # Global exception handler
│   │   └── Logger.kt                # Startup banner & logging
│   ├── Models/
│   │   └── UserModel.kt             # MongoDB user document model
│   └── Commands/
│       ├── Prefix/
│       │   └── Ping.kt              # Example prefix command
│       └── Slash/
│           └── Ping.kt              # Example slash command
```

## API Reference

### Bot Initialization

```kotlin
// Main.kt
suspend fun main() {
    val config = Config()
    val bot = Bot(config)
    AntiCrash.setup()
    bot.start()
}
```

### Creating Slash Commands

Create a file in `src/Commands/Slash/<Name>.kt`:

```kotlin
import dev.kord.core.event.InteractionCreateEvent

object Ping : SlashCommand("ping", "Replies with Pong!") {
    override suspend fun execute(event: InteractionCreateEvent) {
        event.interaction.respond { content = "Pong! 🏓" }
    }
}
```

### Creating Prefix Commands

Create a file in `src/Commands/Prefix/<Name>.kt`:

```kotlin
import dev.kord.core.entity.Message

object Ping {
    const val name = "ping"

    suspend fun execute(message: Message, args: List<String>) {
        message.channel.createMessage("Pong! 🏓")
    }
}
```

## Adding Commands

Commands are auto-discovered at startup — no manual wiring needed. Drop a new object in `Commands/Slash/` or `Commands/Prefix/` implementing the expected interface, and the handler registers it automatically.

## Database Edition

This repository uses **MongoDB** with the coroutine driver. If you prefer a relational database, check out the **SQL Edition**:

[![Discord-Handler-Kt-Sequelize](https://img.shields.io/badge/Discord--Handler--Kt--Sequelize-Exposed%20ORM-blue)](https://github.com/RealMtrx/Discord-Handler-Kt-Sequelize)

The SQL edition replaces `Mongo.kt` with `Database.kt` using **Exposed ORM** with PostgreSQL (configurable to SQLite, MySQL, etc.).

## Related Repositories

The Discord Handler ecosystem spans **26 repositories** across 13 programming languages — each with a MongoDB edition and a SQL (Sequelize) edition.

### Core Framework (MongoDB)

| Language | Repository |
|---|---|
| TypeScript | [Discord-Handler-Ts](https://github.com/RealMtrx/Discord-Handler-Ts) |
| JavaScript | [Discord-Handler-Js](https://github.com/RealMtrx/Discord-Handler-Js) |
| Python | [Discord-Handler-Py](https://github.com/RealMtrx/Discord-Handler-Py) |
| Java | [Discord-Handler-Java](https://github.com/RealMtrx/Discord-Handler-Java) |
| Kotlin | [Discord-Handler-Kt](https://github.com/RealMtrx/Discord-Handler-Kt) |
| C++ | [Discord-Handler-Cpp](https://github.com/RealMtrx/Discord-Handler-Cpp) |
| C# | [Discord-Handler-Cs](https://github.com/RealMtrx/Discord-Handler-Cs) |
| Go | [Discord-Handler-Go](https://github.com/RealMtrx/Discord-Handler-Go) |
| Rust | [Discord-Handler-Rs](https://github.com/RealMtrx/Discord-Handler-Rs) |
| Dart | [Discord-Handler-Dart](https://github.com/RealMtrx/Discord-Handler-Dart) |
| PHP | [Discord-Handler-Php](https://github.com/RealMtrx/Discord-Handler-Php) |
| Ruby | [Discord-Handler-Rb](https://github.com/RealMtrx/Discord-Handler-Rb) |
| Lua | [Discord-Handler-Lua](https://github.com/RealMtrx/Discord-Handler-Lua) |

### Database Editions (SQL)

| Language | Repository |
|---|---|
| TypeScript | [Discord-Handler-Ts-Sequelize](https://github.com/RealMtrx/Discord-Handler-Ts-Sequelize) |
| JavaScript | [Discord-Handler-Js-Sequelize](https://github.com/RealMtrx/Discord-Handler-Js-Sequelize) |
| Python | [Discord-Handler-Py-Sequelize](https://github.com/RealMtrx/Discord-Handler-Py-Sequelize) |
| Java | [Discord-Handler-Java-Sequelize](https://github.com/RealMtrx/Discord-Handler-Java-Sequelize) |
| Kotlin | [Discord-Handler-Kt-Sequelize](https://github.com/RealMtrx/Discord-Handler-Kt-Sequelize) |
| C++ | [Discord-Handler-Cpp-Sequelize](https://github.com/RealMtrx/Discord-Handler-Cpp-Sequelize) |
| C# | [Discord-Handler-Cs-Sequelize](https://github.com/RealMtrx/Discord-Handler-Cs-Sequelize) |
| Go | [Discord-Handler-Go-Sequelize](https://github.com/RealMtrx/Discord-Handler-Go-Sequelize) |
| Rust | [Discord-Handler-Rs-Sequelize](https://github.com/RealMtrx/Discord-Handler-Rs-Sequelize) |
| Dart | [Discord-Handler-Dart-Sequelize](https://github.com/RealMtrx/Discord-Handler-Dart-Sequelize) |
| PHP | [Discord-Handler-Php-Sequelize](https://github.com/RealMtrx/Discord-Handler-Php-Sequelize) |
| Ruby | [Discord-Handler-Rb-Sequelize](https://github.com/RealMtrx/Discord-Handler-Rb-Sequelize) |
| Lua | [Discord-Handler-Lua-Sequelize](https://github.com/RealMtrx/Discord-Handler-Lua-Sequelize) |

> **Hub repository:** [Discord-Handler](https://github.com/RealMtrx/Discord-Handler) — the multi-language overview and documentation hub.

## License

This project is licensed under the MIT License — see the [LICENSE](https://github.com/RealMtrx/Discord-Handler-Kt/blob/main/LICENSE) file for details.

---

Built by **Mtrx** — Discord: **0hu2**

[![Discord-Handler](https://img.shields.io/badge/Discord--Handler-Ecosystem%20Hub-5865F2)](https://github.com/RealMtrx/Discord-Handler)
