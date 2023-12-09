package me.ayitinya.grenes.data.location

import me.ayitinya.grenes.data.Db
import org.jetbrains.exposed.sql.update

class DefaultLocationDao : LocationDao {
    private fun locationEntityToLocation(locationEntity: LocationEntity?): Location? = locationEntity?.let {
        Location(
            id = it.id.toString(),
            city = it.city,
            country = it.country
        )
    }

    private fun List<LocationEntity>.toLocations(): List<Location> = map {
        locationEntityToLocation(it) ?: throw Exception("Location not found")
    }

    override suspend fun allLocations(): List<LocationEntity> = Db.query {
        LocationEntity.all().toList()
    }

    override suspend fun addNewLocation(city: String, country: String): LocationEntity? = Db.query {
        return@query try {
            LocationEntity.new {
                this.city = city
                this.country = country
            }

        } catch (exception: Exception) {
            null
        }
    }

    override suspend fun editLocation(location: LocationEntity): Boolean = Db.query {
        Locations.update({ Locations.id eq location.id }) {
            it[city] = location.city
            it[country] = location.country
        } > 0
    }
}