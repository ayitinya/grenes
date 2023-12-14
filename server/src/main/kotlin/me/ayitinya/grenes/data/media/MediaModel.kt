package me.ayitinya.grenes.data.media

import kotlinx.datetime.Clock
import me.ayitinya.grenes.data.users.UsersTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.UUID

enum class FileTypes {
    IMAGE,
    VIDEO,
}

object MediaTable : UUIDTable() {
    val fileUrl = varchar("fileUrl", 255)
    val type = enumerationByName("type", 10, FileTypes::class).default(FileTypes.IMAGE)
    val createdAt = timestamp("createdAt").default(Clock.System.now())
    val user = reference("user", UsersTable.uid)
}

//class Media(id: EntityID<UUID>) : UUIDEntity(id) {
//    companion object : UUIDEntityClass<Media>(MediaTable)
//
//    var fileUrl by MediaTable.fileUrl
//    var type by MediaTable.type
//    var createdAt by MediaTable.createdAt
//    var user by UserEntity referencedOn MediaTable.user
//}