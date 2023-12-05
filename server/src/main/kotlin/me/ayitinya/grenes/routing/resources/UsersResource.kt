package me.ayitinya.grenes.routing.resources

import io.ktor.resources.Resource

@Resource("/users")
internal class UsersResource() {
    @Resource("/{id}")
    data class Id(val parent: UsersResource = UsersResource(), val id: Int)

    @Resource("/login")
    data class Login(
        val parent: UsersResource = UsersResource(), val email: String, val password: String
    )

    @Resource("/me")
    data class SessionUserDetails(val parent: UsersResource = UsersResource())

}