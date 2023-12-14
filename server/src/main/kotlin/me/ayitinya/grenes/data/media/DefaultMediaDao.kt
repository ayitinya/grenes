package me.ayitinya.grenes.data.media

import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.users.UsersTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.UUID

class DefaultMediaDao : MediaDao {
    override suspend fun insert(fileUrl: String, types: FileTypes, uid: String) {
        Db.query {
            MediaTable.insert {
                it[MediaTable.fileUrl] = fileUrl
                it[MediaTable.type] = types
                it[MediaTable.user] = UsersTable.select { UsersTable.uid eq uid }.single()[UsersTable.uid]
            }
        }
    }

    override suspend fun delete(id: UUID) {
        return Db.query {
            MediaTable.deleteWhere { MediaTable.id eq id }
        }
    }

//    override suspend fun getMediaByUserId(uid: String): List<Media> = Db.query {
//        return@query Media.find { MediaTable.user eq uid }.toList()
//    }
//
//    override suspend fun getMediaByUserIdAndType(uid: String, type: FileTypes): List<Media> =
//        Db.query {
//            return@query Media.find {(MediaTable.user eq uid) and (MediaTable.type eq type)}.toList()
//        }
//
//    override suspend fun getMediaById(id: UUID): Media? = Db.query {
//        return@query Media.findById(id)
//    }
}