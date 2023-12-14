package me.ayitinya.grenes.data.users

import io.ktor.server.auth.*
import kotlinx.datetime.Clock
import me.ayitinya.grenes.data.location.LocationEntity
import me.ayitinya.grenes.data.location.Locations
import me.ayitinya.grenes.data.media.MediaTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Table.Dual.defaultExpression
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.UUID


internal object UsersTable : Table() {
    val displayName = varchar("displayName", length = 64).nullable()
    val email = varchar("email", length = 64).uniqueIndex()
    val password = varchar("password", 256).nullable()
    val createdAt = timestamp("createdAt")
    val location = reference("locationId", Locations).nullable()

    //    val profileAvatar = reference("profileAvatar", MediaTable.id).nullable()
    val uid = varchar("uid", 64).uniqueIndex()

    override val primaryKey = PrimaryKey(uid)
}
