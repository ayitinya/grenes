package me.ayitinya.grenes.data.users

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.ayitinya.grenes.data.location.Location

@Serializable
data class User(
    val uid: String,
    val displayName: String? = null,
    val email: String,
    val createdAt: Instant,
    val location: Location? = null,
    val profileAvatar: String? = null
)