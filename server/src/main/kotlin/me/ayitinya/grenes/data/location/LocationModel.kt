package me.ayitinya.grenes.data.location

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

internal object Locations : UUIDTable() {
    val city = varchar("city", length = 64)
    val country = varchar("country", length = 64)


    init {
        uniqueIndex(city, country)
    }
}

class Location(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object: UUIDEntityClass<Location>(Locations)

    var city by Locations.city
    var country by Locations.country
}