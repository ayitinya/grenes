package me.ayitinya.grenes.data.users

import kotlinx.datetime.Clock
import me.ayitinya.grenes.auth.Hashers.getHexDigest
import me.ayitinya.grenes.data.Database
import me.ayitinya.grenes.data.location.Location
import org.jetbrains.exposed.sql.update
import java.util.UUID


object DefaultUserDao : UserDao {
    private fun userEntityToUser(userEntity: UserEntity?): User? = userEntity?.let {
        User(
            id = userEntity.id.toString(),
            fullName = it.fullName,
            displayName = userEntity.displayName,
            email = userEntity.email,
            createdAt = userEntity.createdAt,
            location = userEntity.location,
        )
    }

    private fun List<UserEntity>.toUsers(): List<User> = map {
        userEntityToUser(it) ?: throw Exception("User not found")
    }

    override suspend fun allUsers(): List<User> = Database.dbQuery {
        UserEntity.all().toList().toUsers()
    }

    override suspend fun authenticateUser(email: String, password: String): User? = Database.dbQuery {
        UserEntity.find { Users.email eq email }.firstOrNull()?.let {
            if (it.password == getHexDigest(password)) {
                return@dbQuery it.let(::userEntityToUser)
            } else {
                return@dbQuery null
            }
        }
    }

    override suspend fun addNewUser(
        fullName: String, displayName: String, email: String, password: String, locationId: UUID
    ): UserEntity = Database.dbQuery {
        Location.findById(locationId).let {
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

    override suspend fun editUser(userEntity: UserEntity) = Database.dbQuery {
        Users.update({ Users.id eq userEntity.id }) {
            it[this.fullName] = userEntity.fullName
            it[this.displayName] = userEntity.displayName
            it[this.email] = userEntity.email
            it[this.createdAt] = userEntity.createdAt
        } > 0
    }

    override suspend fun deleteUser(userId: UUID): Boolean = Database.dbQuery {
        val userEntity = UserEntity.findById(userId)

        if (userEntity != null) {
            userEntity.delete()
            return@dbQuery true
        } else {
            return@dbQuery false
        }
    }

    override suspend fun getUserByEmail(email: String): User? = Database.dbQuery {
        return@dbQuery UserEntity.find { Users.email eq email }.firstOrNull()
            .let(::userEntityToUser)
    }

    override suspend fun getUserById(userId: UUID): User? = Database.dbQuery {
        UserEntity.findById(userId).let(::userEntityToUser)
    }
}