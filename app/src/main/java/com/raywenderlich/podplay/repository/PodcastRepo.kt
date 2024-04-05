

package com.raywenderlich.podplay.repository

import com.raywenderlich.podplay.model.Episode
import com.raywenderlich.podplay.model.Podcast
import com.raywenderlich.podplay.service.RssFeedResponse
import com.raywenderlich.podplay.service.RssFeedService
import com.raywenderlich.podplay.util.DateUtils

class PodcastRepo(private var feedService: RssFeedService) {

  suspend fun getPodcast(feedUrl: String): Podcast? {
    var podcast: Podcast? = null
    val feedResponse = feedService.getFeed(feedUrl)
    if (feedResponse != null) {
      podcast = rssResponseToPodcast(feedUrl, "", feedResponse)
    }
    return podcast
  }

  private fun rssItemsToEpisodes(episodeResponses: List<RssFeedResponse.EpisodeResponse>): List<Episode> {
    return episodeResponses.map {
      Episode(
          it.guid ?: "",
          it.title ?: "",
          it.description ?: "",
          it.url ?: "",
          it.type ?: "",
          DateUtils.xmlDateToDate(it.pubDate),
          it.duration ?: ""
      )
    }
  }

  private fun rssResponseToPodcast(
      feedUrl: String, imageUrl: String, rssResponse: RssFeedResponse
  ): Podcast? {
    // 1
    val items = rssResponse.episodes ?: return null
    // 2
    val description = if (rssResponse.description == "")
      rssResponse.summary else rssResponse.description
    // 3
    return Podcast(feedUrl, rssResponse.title, description, imageUrl,
        rssResponse.lastUpdated, episodes = rssItemsToEpisodes(items))
  }
}
