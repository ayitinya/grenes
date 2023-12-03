package me.ayitinya.grenes.data.location

interface LocationDao {
    suspend fun allLocations(): List<Location>

    suspend fun addNewLocation(location: Location): Location?

    suspend fun editLocation(location: Location): Boolean
}