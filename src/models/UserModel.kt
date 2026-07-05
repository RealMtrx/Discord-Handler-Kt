package models

import database.Mongo
import kotlinx.coroutines.flow.first

data class UserModel(
    val userId: String,
    val points: Int = 0
) {
    companion object {
        suspend fun findByUserId(userId: String): UserModel? {
            val db = Mongo.getClient()?.getDatabase("discord") ?: return null
            val collection = db.getCollection<org.bson.Document>("users")
            val doc = collection.find(org.bson.Document("userId", userId)).first()
            return doc?.let { UserModel(it.getString("userId"), it.getInteger("points", 0)) }
        }

        suspend fun save(user: UserModel) {
            val db = Mongo.getClient()?.getDatabase("discord") ?: return
            val collection = db.getCollection<org.bson.Document>("users")
            collection.replaceOne(
                org.bson.Document("userId", user.userId),
                org.bson.Document("userId", user.userId).append("points", user.points),
                com.mongodb.client.model.ReplaceOptions().upsert(true)
            )
        }
    }
}
