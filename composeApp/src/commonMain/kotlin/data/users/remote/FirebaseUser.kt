package data.users.remote

data class FirebaseUser(
    val uid: String,
    val email: String,
    val displayName: String,
    val photoUrl: String? = null,
    val isEmailVerified: Boolean = true,
)