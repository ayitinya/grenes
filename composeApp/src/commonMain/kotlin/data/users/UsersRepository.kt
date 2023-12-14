package data.users

interface UsersRepository {
    suspend fun createUserWithEmailAndPassword(email: String, password: String)

    suspend fun updateProfile(displayName: String, city: String, country: String)
}