package me.ayitinya.grenes.routing.resources

import io.ktor.resources.Resource

@Resource("/users")
internal class UsersResource {
    @Resource("/me")
    data class SessionUserDetails(val parent: UsersResource = UsersResource())

}