package me.ayitinya.grenes.routing

import io.ktor.resources.*

@Resource("/challenge")
class ChallengeResource(
    val suggestedBy: String? = null,
    val challengeType: List<String>? = null,
    val date: String? = null,
    val isActive: Boolean? = null,
) {
    @Resource("/create")
    class Create(val parent: ChallengeResource = ChallengeResource())

    @Resource("/types")
    class Types(val parent: ChallengeResource = ChallengeResource())

    @Resource("{uid}")
    class Detail(val parent: ChallengeResource = ChallengeResource(), val uid: String)
}