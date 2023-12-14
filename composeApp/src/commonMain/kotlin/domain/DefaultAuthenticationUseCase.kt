package domain

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.*

class DefaultAuthenticationUseCase : AuthenticationUseCase {
    private val auth: FirebaseAuth = Firebase.auth

    override suspend fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }


    override suspend fun sendSignInLinkToEmail(email: String, onEmailSent: () -> Unit, onError: (String) -> Unit) {
        val actionCodeSettings = ActionCodeSettings(
            url = "https://grenes.page.link/finishSignUp",
            canHandleCodeInApp = true,
            androidPackageName = AndroidPackageName(
                packageName = "me.ayitinya.grenes", installIfNotAvailable = true, minimumVersion = null
            ),
            iOSBundleId = "me.ayitinya.grenes"
        )

        try {
            auth.sendSignInLinkToEmail(email, actionCodeSettings)
            onEmailSent()
        } catch (e: Exception) {
            onError(e.message ?: "Unknown error")
        }
    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun fetchSignInMethodsForEmail(email: String): List<String> {
        return auth.fetchSignInMethodsForEmail(email)
    }
}