package me.ayitinya.grenes.routing.resources

import io.ktor.resources.Resource

@Resource("/users")
internal class UsersResource {
    @Resource("/me")
    data class SessionUserDetails(val parent: UsersResource = UsersResource())

    @Resource("/register")
    data class Register(val parent: UsersResource = UsersResource())

    @Resource("/create-user-with-uid")
    data class CreateUserWithUid(val parent: UsersResource = UsersResource())
}