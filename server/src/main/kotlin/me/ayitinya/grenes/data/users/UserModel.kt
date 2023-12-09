package me.ayitinya.grenes.data.users

import me.ayitinya.grenes.data.location.LocationEntity
import me.ayitinya.grenes.data.location.Locations
import me.ayitinya.grenes.data.media.Media
import me.ayitinya.grenes.data.media.MediaTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.UUID


internal object UsersTable : UUIDTable() {
    val fullName = varchar("fullName", length = 64)
    val displayName = varchar("displayName", length = 64)
    val email = varchar("email", length = 64).uniqueIndex()
    val password = varchar("password", 256)
    val createdAt = timestamp("createdAt")
    val location = reference("locationId", Locations).nullable()
    val profileAvatar = reference("profileAvatar", MediaTable).nullable()
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UsersTable)

    var fullName by UsersTable.fullName
    var displayName by UsersTable.displayName
    var email by UsersTable.email
    var password by UsersTable.password
    var createdAt by UsersTable.createdAt
    var location by LocationEntity optionalReferencedOn UsersTable.location
    var profileAvatar by Media optionalReferencedOn UsersTable.profileAvatar
}
