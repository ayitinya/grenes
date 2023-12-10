package domain

import data.users.remote.FirebaseUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.auth.ActionCodeSettings
import dev.gitlive.firebase.auth.AndroidPackageName

class DefaultAuthenticationUseCase : AuthenticationUseCase {
    override suspend fun getCurrentUser(): FirebaseUser? {
        TODO("Not yet implemented")
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
            Firebase.auth.sendSignInLinkToEmail(email, actionCodeSettings)
            onEmailSent()
        } catch (e: Exception) {
            onError(e.message ?: "Unknown error")
        }


    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSignInMethodsForEmail(email: String): List<String> {
        return Firebase.auth.fetchSignInMethodsForEmail(email)
    }
}