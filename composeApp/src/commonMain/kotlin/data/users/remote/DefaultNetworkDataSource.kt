package data.users.remote

import dev.gitlive.firebase.auth.FirebaseAuth
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.Clock
import me.ayitinya.grenes.data.users.User

class DefaultNetworkDataSource(private val httpClient: HttpClient, private val auth: FirebaseAuth) : NetworkDataSource {

    override suspend fun getCurrentUser(): User {
        val currentUser = auth.currentUser ?: throw Exception("User is not authenticated")
        return User(
            uid = currentUser.uid,
            displayName = currentUser.displayName ?: "",
            email = currentUser.email ?: "",
            createdAt = Clock.System.now(),
            profileAvatar = currentUser.photoURL
        )
    }

    override suspend fun sendSignInLinkToEmail(email: String, onEmailSent: () -> Unit, onError: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
        try {
            val authResult = auth.createUserWithEmailAndPassword(email, password)
            val token = authResult.user?.getIdToken(true)

            if (authResult.user != null && token != null) {
                httpClient.post("/users/create-user-with-uid") {
                    contentType(ContentType.Application.Json)
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                    setBody(
                        User(
                            uid = authResult.user!!.uid,
                            displayName = authResult.user!!.displayName ?: "",
                            email = authResult.user!!.email ?: "",
                            createdAt = Clock.System.now(),
                            profileAvatar = authResult.user!!.photoURL
                        )
                    )
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.message} - ${e.cause}")
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSignInMethodsForEmail(email: String): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(displayName: String, city: String, country: String) {
        try {
            auth.currentUser.let { firebaseUser ->
                firebaseUser?.getIdToken(true)?.let { token ->
                    httpClient.post("/users/me") {
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $token")
                        }
                        contentType(ContentType.Application.Json)
                        setBody(
                            User(
                                uid = firebaseUser.uid,
                                displayName = displayName,
                                email = firebaseUser.email ?: "",
                                createdAt = Clock.System.now(),
                                profileAvatar = firebaseUser.photoURL,
                            )
                        )
                    }
                }
            }
        } catch (exception: Exception) {
            println("Error: ${exception.message} - ${exception.cause}")
            exception.printStackTrace()
            throw exception
        }
    }
}

// you can also use firebase cloud functions to notify the backend of the created user
// or send the information to the backend server to create the firebase user
// then login with the email and password on the frontend