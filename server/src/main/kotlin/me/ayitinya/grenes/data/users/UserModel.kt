package me.ayitinya.grenes.data.users

import me.ayitinya.grenes.data.location.Location
import me.ayitinya.grenes.data.location.Locations
import me.ayitinya.grenes.data.media.Media
import me.ayitinya.grenes.data.media.Medias
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.UUID


internal object Users : UUIDTable() {
    val fullName = varchar("fullName", length = 64)
    val displayName = varchar("displayName", length = 64)
    val email = varchar("email", length = 64)
    val password = varchar("password", 256)
    val createdAt = timestamp("createdAt")
    val location = reference("locationId", Locations)
    val profileAvatar = reference("profileAvatar", Medias).nullable()
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(Users)

    var fullName by Users.fullName
    var displayName by Users.displayName
    var email by Users.email
    var password by Users.password
    var createdAt by Users.createdAt
    var location by Location referencedOn Users.location
    var profileAvatar by Media optionalReferencedOn Users.profileAvatar
}
