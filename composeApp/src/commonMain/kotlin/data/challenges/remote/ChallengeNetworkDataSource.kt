package data.challenges.remote

import me.ayitinya.grenes.data.challenges.Challenge
import me.ayitinya.grenes.data.challenges.ChallengeCreation

interface ChallengeNetworkDataSource {
    suspend fun getChallenges(): List<Challenge>

    suspend fun getChallenge(uid: String): Challenge?

    suspend fun createChallenge(challenge: ChallengeCreation): Challenge

    suspend fun updateChallenge(challenge: Challenge): Challenge

    suspend fun deleteChallenge(uid: String)

}