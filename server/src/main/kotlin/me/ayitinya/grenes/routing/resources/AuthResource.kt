package me.ayitinya.grenes.routing.resources

import io.ktor.resources.Resource

@Resource("/auth")
internal class AuthResource() {
    @Resource("login")
     data class Login(val parent: AuthResource = AuthResource())

    @Resource("/logout")
     data class Logout(val parent: AuthResource = AuthResource())

    @Resource("/register")
     data class Register(val parent: AuthResource = AuthResource())
}