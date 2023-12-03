package me.ayitinya.grenes.data.location

import me.ayitinya.grenes.data.Database
import org.jetbrains.exposed.sql.update

object DefaultLocationDao : LocationDao {

    override suspend fun allLocations(): List<Location> = Database.dbQuery {
        Location.all().toList()
    }

    override suspend fun addNewLocation(location: Location): Location? = Database.dbQuery {
        return@dbQuery try {
            Location.new {
                city = location.city
                country = location.country
            }

        } catch (exception: Exception) {
            null
        }
    }

    override suspend fun editLocation(location: Location): Boolean = Database.dbQuery {
        Locations.update({ Locations.id eq location.id }) {
            it[city] = location.city
            it[country] = location.country
        } > 0
    }
}