package me.ayitinya.grenes.routing

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/users")
class UsersResource {
    @Serializable
    @Resource("/me")
    data class SessionUserDetails(val parent: UsersResource = UsersResource())

    @Serializable
    @Resource("/register")
    data class Register(val parent: UsersResource = UsersResource())

    @Serializable
    @Resource("/create-user-with-uid")
    data class CreateUserWithUid(val parent: UsersResource = UsersResource())

    @Serializable
    @Resource("/")
    data class UserDetails(val parent: UsersResource, val uid: String)
}