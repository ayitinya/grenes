package data.users

import data.users.remote.NetworkDataSource

class DefaultUsersRepository(private val networkDataSource: NetworkDataSource) : UsersRepository {
    override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
        networkDataSource.createUserWithEmailAndPassword(email = email, password = password)
    }

    override suspend fun updateProfile(displayName: String, city: String, country: String) {
        networkDataSource.updateProfile(displayName = displayName, city = city, country = country)
    }
}