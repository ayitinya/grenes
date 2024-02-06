package me.ayitinya.grenes.data.feed

import kotlinx.datetime.Clock
import me.ayitinya.grenes.data.challenges.Challenges
import me.ayitinya.grenes.data.media.MediaTable
import me.ayitinya.grenes.data.users.User
import me.ayitinya.grenes.data.users.UsersTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp


internal object FeedsTable : UUIDTable() {
    val content = largeText("content", eagerLoading = true)
    val createdAt = timestamp("createdAt").clientDefault { Clock.System.now() }
    val user =
        reference("user", UsersTable.uid, onDelete = ReferenceOption.NO_ACTION, onUpdate = ReferenceOption.NO_ACTION)
    val challengeSubmission = bool("challengeSubmission").default(false)
    val challenge = optReference("challengeId", Challenges)
}

internal object FeedMedia : UUIDTable() {
    val media = reference("media", MediaTable)
    val feed = reference("feed", FeedsTable)

    init {
        uniqueIndex(media, feed)
    }
}

internal object FeedComments : UUIDTable() {
    val feed = reference("feed", FeedsTable)
    val user = reference("user", UsersTable.uid)
    val content = mediumText("content", eagerLoading = true)
    val createdAt = timestamp("createdAt").clientDefault { Clock.System.now() }
}

internal object ReactionsTable : UUIDTable() {
    val user = reference("user", UsersTable.uid)
    val reactable = enumerationByName<Reactables>("reactable", 10)
    val reactionType = enumerationByName<ReactionType>("reactionType", 10).clientDefault { ReactionType.LIKE }
    val reactionTo = varchar("reactionTo", 255)

    init {
        uniqueIndex(user, reactable, reactionTo)
    }
}

fun ResultRow.toFeed(reactions: Long, comments: Long = 0, shares: Long= 0, user: User, media: List<String>): Feed {
    return Feed(
        content = this[FeedsTable.content],
        createdAt = this[FeedsTable.createdAt],
        reactions = reactions,
        comments = comments,
        shares = shares,
        user = user,
        media = media,
        isChallengeSubmission = this[FeedsTable.challengeSubmission],
        challenge = this[FeedsTable.challenge]?.toString()
    )
}

fun ResultRow.toFeedComment(feed: Feed, user: User, reactions: List<Reaction>): FeedComment {
    return FeedComment(
        feed = feed,
        content = this[FeedComments.content],
        createdAt = this[FeedComments.createdAt],
        user = user,
        reactions = reactions
    )
}

fun ResultRow.toReaction(user: User): Reaction {
    return Reaction(
        reactionType = this[ReactionsTable.reactionType],
        user = user
    )
}