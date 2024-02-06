package me.ayitinya.grenes.data.challenges

import kotlinx.datetime.Instant
import java.util.*

interface ChallengeDao {
    suspend fun create(
        title: String,
        description: String,
        suggestedBy: String?,
        challengeTypes: List<UUID>,
        startDate: Instant?,
        endDate: Instant?
    ): Challenge?

    suspend fun createChallengeType(name: String): ChallengeType?

    suspend fun getAllChallengeTypes(): List<ChallengeType>

    suspend fun read(uid: UUID): Challenge?

    suspend fun read(
        challengeType: List<String>? = null,
        date: Instant? = null,
        suggestedBy: String? = null,
        isActive: Boolean? = null
    ): List<Challenge>

    suspend fun readByType(type: UUID): List<Challenge>

    suspend fun readBySuggestedBy(suggestedBy: String): List<Challenge>

    suspend fun readByTypeAndSuggestedBy(type: UUID, suggestedBy: String): List<Challenge>

    suspend fun update(uid: UUID, challenge: Challenge): Int

    suspend fun delete(uid: UUID): Int
}