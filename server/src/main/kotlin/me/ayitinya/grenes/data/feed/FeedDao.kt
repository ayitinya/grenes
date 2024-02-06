package me.ayitinya.grenes.data.feed

interface FeedDao {
    suspend fun createFeed(feed: FeedCreation): Feed?
    suspend fun getFeed(feedId: String): Feed?
    suspend fun getFeeds(): List<Feed>
    suspend fun updateFeed(feedId: String, feed: FeedCreation)
    suspend fun deleteFeed(feedId: String)
    suspend fun getFeedComments(feedId: String): List<FeedComment>
    suspend fun createFeedComment(feedId: String, feedComment: FeedCommentCreation): FeedComment?
    suspend fun updateFeedComment(feedId: String, feedCommentId: String, feedComment: FeedCommentCreation): FeedComment?
    suspend fun deleteFeedComment(feedCommentId: String)
    suspend fun getFeedReactions(feedId: String): List<Reaction>
    suspend fun createFeedReaction(feedId: String, reactionType: ReactionType, userId: String): Reaction
    suspend fun deleteReaction(reactionId: String)
    suspend fun getFeedCommentReactions(feedCommentId: String): List<Reaction>
    suspend fun createFeedCommentReaction(feedCommentId: String, userId: String): Reaction
}