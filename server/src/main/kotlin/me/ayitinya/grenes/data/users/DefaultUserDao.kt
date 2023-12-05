package me.ayitinya.grenes.data.users

import kotlinx.datetime.Clock
import me.ayitinya.grenes.auth.Hashers.getHexDigest
import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.location.LocationEntity
import org.jetbrains.exposed.sql.update
import java.util.UUID


class DefaultUserDao : UserDao {
    override suspend fun allUsers(): List<User> = Db.dbQuery {
        UserEntity.all().toList().toUsers()
    }

    override suspend fun authenticateUser(email: String, password: String): User? = Db.dbQuery {
        UserEntity.find { UsersTable.email eq email }.firstOrNull()?.let {
            if (it.password == getHexDigest(password)) {
                return@dbQuery it.let(::userEntityToUser)
            } else {
                return@dbQuery null
            }
        }
    }

    override suspend fun addNewUser(
        fullName: String, displayName: String, email: String, password: String, locationId: UUID
    ): UserEntity = Db.dbQuery {
        LocationEntity.findById(locationId).let {
            if (it == null) {
                throw Exception("Location not found")
            }

            UserEntity.new {
                this.fullName = fullName
                this.displayName = displayName
                this.email = email
                this.password = getHexDigest(password)
                this.createdAt = Clock.System.now()
                this.location = it
            }
        }

    }

    override suspend fun editUser(userEntity: UserEntity) = Db.dbQuery {
        UsersTable.update({ UsersTable.id eq userEntity.id }) {
            it[this.fullName] = userEntity.fullName
            it[this.displayName] = userEntity.displayName
            it[this.email] = userEntity.email
            it[this.createdAt] = userEntity.createdAt
        } > 0
    }

    override suspend fun deleteUser(userId: UUID): Boolean = Db.dbQuery {
        val userEntity = UserEntity.findById(userId)

        if (userEntity != null) {
            userEntity.delete()
            return@dbQuery true
        } else {
            return@dbQuery false
        }
    }

    override suspend fun getUserByEmail(email: String): User? = Db.dbQuery {
        return@dbQuery UserEntity.find { UsersTable.email eq email }.firstOrNull()
            .let(::userEntityToUser)
    }

    override suspend fun getUserById(userId: UUID): User? = Db.dbQuery {
        UserEntity.findById(userId).let(::userEntityToUser)
    }
}