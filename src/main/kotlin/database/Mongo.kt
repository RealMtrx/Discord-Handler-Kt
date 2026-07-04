package database

import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoClient
import config.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document

object Mongo {
    private var client: MongoClient? = null

    suspend fun connect(config: Config): Boolean {
        return try {
            client = MongoClient.create(config.mongoUri)
            val db = client!!.getDatabase("discord")
            db.runCommand(Document("ping", 1))
            println("\u001B[32m[System] MongoDB connected\u001B[0m")
            true
        } catch (e: MongoException) {
            println("\u001B[31m[MongoDB] Connection failed: ${e.message}\u001B[0m")
            false
        }
    }

    fun getClient(): MongoClient? = client

    suspend fun disconnect() {
        withContext(Dispatchers.IO) {
            client?.close()
        }
    }
}
