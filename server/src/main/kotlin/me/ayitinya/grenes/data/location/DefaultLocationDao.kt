package me.ayitinya.grenes.data.location

import me.ayitinya.grenes.data.Db
import org.jetbrains.exposed.sql.update

class DefaultLocationDao : LocationDao {

    override suspend fun allLocations(): List<LocationEntity> = Db.dbQuery {
        LocationEntity.all().toList()
    }

    override suspend fun addNewLocation(location: LocationEntity): LocationEntity? = Db.dbQuery {
        return@dbQuery try {
            LocationEntity.new {
                city = location.city
                country = location.country
            }

        } catch (exception: Exception) {
            null
        }
    }

    override suspend fun editLocation(location: LocationEntity): Boolean = Db.dbQuery {
        Locations.update({ Locations.id eq location.id }) {
            it[city] = location.city
            it[country] = location.country
        } > 0
    }
}