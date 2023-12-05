package me.ayitinya.grenes.data.users

import me.ayitinya.grenes.data.location.Location

internal fun userEntityToUser(userEntity: UserEntity?): User? = userEntity?.let {
    User(
        id = it.id.toString(),
        fullName = it.fullName,
        displayName = it.displayName,
        email = it.email,
        createdAt = it.createdAt,
        location = Location(
            id = it.location.id.toString(),
            city = it.location.city,
            country = it.location.country
        ),
    )
}

internal fun List<UserEntity>.toUsers(): List<User> = map {
    userEntityToUser(it) ?: throw Exception("User not found")
}