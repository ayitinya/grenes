package domain

import data.users.remote.FirebaseUser

interface AuthenticationUseCase {
    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun sendSignInLinkToEmail(
        email: String,
        onEmailSent: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun createUserWithEmailAndPassword(email: String, password: String)

    suspend fun signInWithEmailAndPassword(email: String, password: String)

    suspend fun fetchSignInMethodsForEmail(email: String): List<String>
}