package me.ayitinya.grenes.data.users

interface UserDao {
    suspend fun allUsers(): List<User>

    suspend fun updateUser(uid: String, user: User)

    suspend fun deleteUser(uid: String)

    suspend fun getUserById(uid: String): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun createNewUserWithUidAndEmail(uid: String, email: String)

    suspend fun createNewUserWithEmailAndPassword(
        email: String, password: String,
    )
}