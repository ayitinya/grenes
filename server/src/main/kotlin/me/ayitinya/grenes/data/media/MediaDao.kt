package me.ayitinya.grenes.data.media

import java.util.UUID

interface MediaDao {
    suspend fun insert(
        fileUrl: String,
        types: FileTypes,
        userId: UUID
    ): Media

    suspend fun delete(id: UUID): Boolean

    suspend fun getMediaByUserId(userId: UUID): List<Media>

    suspend fun getMediaByUserIdAndType(userId: UUID, type: FileTypes): List<Media>

    suspend fun getMediaById(id: UUID): Media?
}