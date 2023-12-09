package me.ayitinya.grenes

import kotlinx.datetime.Clock
import me.ayitinya.grenes.data.location.Location
import me.ayitinya.grenes.data.users.User
import java.util.UUID

val usersList = listOf(
    User(
        fullName = "Test User",
        email = "william.henry.moody@my-own-personal-domain.com",
        displayName = "Test User",
        location = Location(
            id = UUID.randomUUID().toString(), city = "Test Location", country = "Test Country"
        ),
        createdAt = Clock.System.now(),
        profileAvatar = null,
        id = UUID.randomUUID().toString()
    ), User(
        fullName = "Test User",
        email = "william.howard.taft@my-own-personal-domain.com",
        displayName = "Test User",
        location = Location(
            id = UUID.randomUUID().toString(), city = "Test", country = "Test Country"
        ),
        createdAt = Clock.System.now(),
        profileAvatar = null,
        id = UUID.randomUUID().toString()
    )
)