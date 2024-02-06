package me.ayitinya.grenes

import kotlinx.datetime.Clock
import me.ayitinya.grenes.data.users.User
import java.util.UUID

val usersList = listOf(
    User(
        uid = UUID.randomUUID().toString(),
        displayName = "Test User",
        email = "william.henry.moody@my-own-personal-domain.com",
        createdAt = Clock.System.now(),
        profileAvatar = null
    ), User(
        uid = UUID.randomUUID().toString(),
        displayName = "Test User",
        email = "william.howard.taft@my-own-personal-domain.com",
        createdAt = Clock.System.now(),
        profileAvatar = null
    )
)