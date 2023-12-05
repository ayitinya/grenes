package me.ayitinya.grenes.data.location

interface LocationDao {
    suspend fun allLocations(): List<LocationEntity>

    suspend fun addNewLocation(location: LocationEntity): LocationEntity?

    suspend fun editLocation(location: LocationEntity): Boolean
}