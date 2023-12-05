package me.ayitinya.grenes.routing.resources

import io.ktor.resources.Resource

@Resource("/auth")
internal class AuthResource() {
    @Resource("/login")
    data class Login(val parent: UsersResource = UsersResource())
}