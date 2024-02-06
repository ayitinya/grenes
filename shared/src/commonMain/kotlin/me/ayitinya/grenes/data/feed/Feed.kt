package me.ayitinya.grenes.data.feed

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.ayitinya.grenes.data.feed.Reactables.COMMENT
import me.ayitinya.grenes.data.feed.Reactables.FEED
import me.ayitinya.grenes.data.users.User

/**
 * Can be a feed or a comment
 *
 * @property FEED
 * @property COMMENT
 */
enum class Reactables {
    FEED, COMMENT
}

/**
 * The different types of reactions
 * user can have on a reactable
 *
 * @property reactionType
 * @property user
 */
enum class ReactionType {
    LIKE, LOVE, FUNNY, LAUGH
}

@Serializable
data class Feed(
    val content: String,
    val createdAt: Instant,
    val reactions: Long,
    val comments: Long,
    val shares: Long,
    val user: User,
    val media: List<String> = emptyList(),

    val isChallengeSubmission: Boolean = false,
    val challenge: String? = null,
)

@Serializable
data class FeedCreation(
    val content: String,
    val media: List<String> = emptyList(),
    val user: User,
    val isChallengeSubmission: Boolean = false,
    val challenge: String? = null,
)

@Serializable
data class FeedComment(
    val feed: Feed,
    val content: String,
    val createdAt: Instant,
    val user: User,
    val reactions: List<Reaction> = emptyList(),
)

@Serializable
data class FeedCommentCreation(
    val content: String,
    val user: User,
)

@Serializable
data class Reaction(
    val reactionType: ReactionType,
    val user: User,
)