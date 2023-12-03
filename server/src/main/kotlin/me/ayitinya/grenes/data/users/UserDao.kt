package me.ayitinya.grenes.data.users

import java.util.UUID

interface UserDao {
    suspend fun authenticateUser(email: String, password: String): User?

    suspend fun allUsers(): List<User>

    suspend fun editUser(userEntity: UserEntity): Boolean

    suspend fun deleteUser(userId: UUID): Boolean

    suspend fun getUserById(userId: UUID): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun addNewUser(
        fullName: String, displayName: String, email: String, password: String, locationId: UUID
    ): UserEntity
}