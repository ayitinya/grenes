package me.ayitinya.grenes.data.media

import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.users.UserEntity
import org.jetbrains.exposed.sql.and
import java.util.UUID

class DefaultMediaDao : MediaDao {
    override suspend fun insert(fileUrl: String, types: FileTypes, userId: UUID) =
        Db.dbQuery {
            val userEntity = UserEntity.findById(userId) ?: throw IllegalStateException("User not found")

            userEntity.let {
                return@dbQuery Media.new {
                    this.fileUrl = fileUrl
                    this.type = types
                    this.user = it
                }
            }
        }

    override suspend fun delete(id: UUID) = Db.dbQuery {
        val media = Media.findById(id) ?: return@dbQuery false
        media.delete()
        true
    }

    override suspend fun getMediaByUserId(userId: UUID): List<Media> = Db.dbQuery {
        return@dbQuery Media.find { Medias.user eq userId }.toList()
    }

    override suspend fun getMediaByUserIdAndType(userId: UUID, type: FileTypes): List<Media> =
        Db.dbQuery {
            return@dbQuery Media.find {(Medias.user eq userId) and (Medias.type eq type)}.toList()
        }

    override suspend fun getMediaById(id: UUID): Media? = Db.dbQuery {
        return@dbQuery Media.findById(id)
    }
}