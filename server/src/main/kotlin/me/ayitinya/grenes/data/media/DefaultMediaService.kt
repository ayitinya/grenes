package me.ayitinya.grenes.data.media

import io.ktor.http.*
import me.ayitinya.grenes.data.feed.FeedId
import me.ayitinya.grenes.data.users.UserId
import me.ayitinya.grenes.storage.Storage

class DefaultMediaService(private val storage: Storage, private val mediaDao: MediaDao) : MediaService {
    override suspend fun uploadMedia(bytes: ByteArray, type: ContentType, uid: UserId, feedId: FeedId) {
        val fileName = "${uid.value}-${System.currentTimeMillis()}.${type.contentType}"
        val mediaUrl = storage.upload(fileName, type, bytes)
        mediaDao.insert(fileUrl = mediaUrl, types = type.contentType, uid = uid, feedId = feedId)
    }
}