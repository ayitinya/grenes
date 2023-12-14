package data.users.remote

import me.ayitinya.grenes.data.users.User

interface NetworkDataSource {

    suspend fun getCurrentUser(): User

    suspend fun sendSignInLinkToEmail(
        email: String,
        onEmailSent: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun createUserWithEmailAndPassword(email: String, password: String)

    suspend fun signInWithEmailAndPassword(email: String, password: String)

    suspend fun fetchSignInMethodsForEmail(email: String): List<String>

    suspend fun updateProfile(displayName: String, city: String, country: String)
}