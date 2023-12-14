package me.ayitinya.grenes.data.media

import java.util.UUID

interface MediaDao {
    suspend fun insert(
        fileUrl: String,
        types: FileTypes,
        uid: String
    )

    suspend fun delete(id: UUID)

//    suspend fun getMediaByUserId(uid: String): List<Media>
//
//    suspend fun getMediaByUserIdAndType(uid: String, type: FileTypes): List<Media>
//
//    suspend fun getMediaById(id: UUID): Media?
}