package me.ayitinya.grenes.data.feed

import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.users.UsersTable
import me.ayitinya.grenes.data.users.toUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class DefaultFeedDao : FeedDao {
    override suspend fun createFeed(feed: FeedCreation): Feed? {
        return Db.query {
            val insertFeedsStatement = FeedsTable.insert {
                it[content] = feed.content
                it[user] = feed.user.uid
                it[challengeSubmission] = feed.isChallengeSubmission
            }

            feed.media.forEach { media ->
                val mediaUUID = UUID.fromString(media)
                FeedMedia.insert {
                    it[FeedMedia.feed] = insertFeedsStatement[FeedsTable.id]
                    it[FeedMedia.media] = mediaUUID
                }
            }

            return@query insertFeedsStatement.resultedValues?.first()?.toFeed(
                reactions = 0,
                comments = 0,
                shares = 0,
                user = feed.user,
                media = feed.media
            )
        }
    }

    override suspend fun getFeed(feedId: String): Feed? {
        return Db.query {
            val feed = FeedsTable.select { FeedsTable.id eq UUID.fromString(feedId) }.firstOrNull()
            if (feed == null) {
                return@query null
            }

            val media =
                FeedMedia.select { FeedMedia.feed eq feed[FeedsTable.id] }.map { it[FeedMedia.media].toString() }
            val user = UsersTable.select { UsersTable.uid eq feed[FeedsTable.user] }.firstOrNull()?.toUser()
            val reactions = ReactionsTable.select {
                ReactionsTable.reactionTo eq feed[FeedsTable.id].toString()
            }.count()
            val comments = FeedComments.select { FeedComments.feed eq feed[FeedsTable.id] }.count()
            val shares = 0L

            return@query feed.toFeed(
                reactions = reactions,
                comments = comments,
                shares = shares,
                user = user!!,
                media = media
            )
        }
    }

    override suspend fun getFeeds(): List<Feed> {
        return Db.query {
            val feeds = FeedsTable.selectAll().toList()
            return@query feeds.map { feed ->
                val media =
                    FeedMedia.select { FeedMedia.feed eq feed[FeedsTable.id] }.map { it[FeedMedia.media].toString() }
                val user = UsersTable.select { UsersTable.uid eq feed[FeedsTable.user] }.firstOrNull()?.toUser()
                val reactions = ReactionsTable.select {
                    ReactionsTable.reactionTo eq feed[FeedsTable.id].toString()
                }.count()
                val comments = FeedComments.select { FeedComments.feed eq feed[FeedsTable.id] }.count()
                val shares = 0L

                return@map feed.toFeed(
                    reactions = reactions,
                    comments = comments,
                    shares = shares,
                    user = user!!,
                    media = media
                )
            }
        }
    }

    override suspend fun updateFeed(feedId: String, feed: FeedCreation) {
        return Db.query {
            FeedsTable.update({ FeedsTable.id eq UUID.fromString(feedId) }) {
                it[content] = feed.content
                it[user] = feed.user.uid
                it[challengeSubmission] = feed.isChallengeSubmission
            }

            feed.media.forEach { media ->
                val mediaUUID = UUID.fromString(media)
                FeedMedia.insertIgnore {
                    it[FeedMedia.feed] = UUID.fromString(feedId)
                    it[FeedMedia.media] = mediaUUID
                }
            }


        }
    }

    override suspend fun deleteFeed(feedId: String) {
        return Db.query {
            FeedsTable.deleteWhere { FeedsTable.id eq UUID.fromString(feedId) }
        }
    }

    override suspend fun getFeedComments(feedId: String): List<FeedComment> {
        return Db.query {
            val feed = FeedsTable.select { FeedsTable.id eq UUID.fromString(feedId) }.firstOrNull()
            if (feed == null) {
                return@query emptyList()
            }

            val comments = FeedComments.select { FeedComments.feed eq feed[FeedsTable.id] }.toList()
            return@query comments.map { comment ->
                val user = UsersTable.select { UsersTable.uid eq comment[FeedComments.user] }.firstOrNull()?.toUser()
                val reactions = ReactionsTable.join(
                    UsersTable,
                    JoinType.INNER,
                    additionalConstraint = { ReactionsTable.user eq UsersTable.uid })
                    .select { ReactionsTable.reactionTo eq comment[FeedComments.id].toString() }
                    .map {
                        val user = it.toUser()
                        val reaction = it.toReaction(user)
                        reaction
                    }

                return@map comment.toFeedComment(
                    feed = feed.toFeed(
                        reactions = 0,
                        comments = 0,
                        shares = 0,
                        user = user!!,
                        media = emptyList()
                    ),
                    reactions = reactions,
                    user = user!!
                )
            }
        }
    }

    override suspend fun createFeedComment(feedId: String, feedComment: FeedCommentCreation): FeedComment? {
        return Db.query {
            val insertFeedCommentStatement = FeedComments.insert {
                it[content] = feedComment.content
                it[user] = feedComment.user.uid
                it[FeedComments.feed] = UUID.fromString(feedId)
            }

            val feed = FeedsTable.select { FeedsTable.id eq UUID.fromString(feedId) }.firstOrNull()
            if (feed == null) {
                return@query null
            }

            val user = UsersTable.select { UsersTable.uid eq feedComment.user.uid }.firstOrNull()?.toUser()
            val reactions = ReactionsTable.select {
                ReactionsTable.reactionTo eq insertFeedCommentStatement[FeedComments.id].toString()
            }.count()

            return@query insertFeedCommentStatement.resultedValues?.first()?.toFeedComment(
                feed = feed.toFeed(
                    reactions = 0,
                    comments = 0,
                    shares = 0,
                    user = user!!,
                    media = emptyList()
                ),
                reactions = emptyList(),
                user = user
            )!!
        }
    }

    override suspend fun updateFeedComment(
        feedId: String,
        feedCommentId: String,
        feedComment: FeedCommentCreation
    ): FeedComment? {
        return Db.query {
            FeedComments.update({ FeedComments.id eq UUID.fromString(feedCommentId) }) {
                it[content] = feedComment.content
                it[user] = feedComment.user.uid
            }

            val feed = FeedsTable.select { FeedsTable.id eq UUID.fromString(feedId) }.firstOrNull()
            if (feed == null) {
                return@query null
            }

            val user = UsersTable.select { UsersTable.uid eq feedComment.user.uid }.firstOrNull()?.toUser()
            val reactions = ReactionsTable.select {
                ReactionsTable.reactionTo eq UUID.fromString(feedCommentId).toString()
            }.count()

            return@query FeedComments.select { FeedComments.id eq UUID.fromString(feedCommentId) }.firstOrNull()
                ?.toFeedComment(
                    feed = feed.toFeed(
                        reactions = reactions,
                        comments = 0,
                        shares = 0,
                        user = user!!,
                        media = emptyList()
                    ),
                    reactions = emptyList(),
                    user = user
                )
        }
    }

    override suspend fun deleteFeedComment(feedCommentId: String) {
        return Db.query {
            FeedComments.deleteWhere { FeedComments.id eq UUID.fromString(feedCommentId) }
        }
    }

    override suspend fun getFeedReactions(feedId: String): List<Reaction> {
        return Db.query {
            val feed = FeedsTable.select { FeedsTable.id eq UUID.fromString(feedId) }.firstOrNull()
            if (feed == null) {
                return@query emptyList()
            }

            return@query ReactionsTable.join(
                UsersTable,
                JoinType.INNER,
                additionalConstraint = { ReactionsTable.user eq UsersTable.uid })
                .select { ReactionsTable.reactionTo eq feed[FeedsTable.id].toString() }
                .map {
                    val user = it.toUser()
                    val reaction = it.toReaction(user)
                    reaction
                }
        }
    }

    override suspend fun createFeedReaction(feedId: String, reactionType: ReactionType, userId: String): Reaction {
        return Db.query {
            val insertReactionStatement = ReactionsTable.insert {
                it[user] = userId
                it[reactable] = Reactables.FEED
                it[ReactionsTable.reactionType] = reactionType
                it[reactionTo] = feedId
            }

            val user = UsersTable.select { UsersTable.uid eq userId }.firstOrNull()?.toUser()

            return@query insertReactionStatement.resultedValues?.first()?.toReaction(user!!)!!
        }
    }

    override suspend fun deleteReaction(reactionId: String) {
        return Db.query {
            ReactionsTable.deleteWhere { ReactionsTable.id eq UUID.fromString(reactionId) }
        }
    }

    override suspend fun getFeedCommentReactions(feedCommentId: String): List<Reaction> {
        return Db.query {
            return@query ReactionsTable.join(
                UsersTable,
                JoinType.INNER,
                additionalConstraint = { ReactionsTable.user eq UsersTable.uid })
                .select { ReactionsTable.reactionTo eq feedCommentId }
                .map {
                    val user = it.toUser()
                    val reaction = it.toReaction(user)
                    reaction
                }
        }
    }

    override suspend fun createFeedCommentReaction(feedCommentId: String, userId: String): Reaction {
        return Db.query {
            val insertReactionStatement = ReactionsTable.insert {
                it[user] = userId
                it[reactable] = Reactables.COMMENT
                it[reactionTo] = feedCommentId
            }

            val user = UsersTable.select { UsersTable.uid eq userId }.firstOrNull()?.toUser()

            return@query insertReactionStatement.resultedValues?.first()?.toReaction(user!!)!!
        }
    }
}