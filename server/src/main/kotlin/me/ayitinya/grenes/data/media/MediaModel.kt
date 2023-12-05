package me.ayitinya.grenes.data.media

import me.ayitinya.grenes.data.users.UserEntity
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

object Medias : UUIDTable() {
    val fileUrl = varchar("fileUrl", 255)
    val type = enumerationByName("type", 10, FileTypes::class)
    val createdAt = timestamp("createdAt")
    val user = reference("user", UsersTable)
}

class Media(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Media>(Medias)

    var fileUrl by Medias.fileUrl
    var type by Medias.type
    var createdAt by Medias.createdAt
    var user by UserEntity referencedOn Medias.user
}