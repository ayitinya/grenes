package me.ayitinya.grenes.data.media

import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.users.UserEntity
import org.jetbrains.exposed.sql.and
import java.util.UUID

class DefaultMediaDao : MediaDao {
    override suspend fun insert(fileUrl: String, types: FileTypes, userId: UUID) =
        Db.query {
            val userEntity = UserEntity.findById(userId) ?: throw IllegalStateException("User not found")

            userEntity.let {
                return@query Media.new {
                    this.fileUrl = fileUrl
                    this.type = types
                    this.user = it
                }
            }
        }

    override suspend fun delete(id: UUID) = Db.query {
        val media = Media.findById(id) ?: return@query false
        media.delete()
        true
    }

    override suspend fun getMediaByUserId(userId: UUID): List<Media> = Db.query {
        return@query Media.find { MediaTable.user eq userId }.toList()
    }

    override suspend fun getMediaByUserIdAndType(userId: UUID, type: FileTypes): List<Media> =
        Db.query {
            return@query Media.find {(MediaTable.user eq userId) and (MediaTable.type eq type)}.toList()
        }

    override suspend fun getMediaById(id: UUID): Media? = Db.query {
        return@query Media.findById(id)
    }
}