
package com.raywenderlich.podplay.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.raywenderlich.podplay.model.Episode
import com.raywenderlich.podplay.model.Podcast

@Dao
interface PodcastDao {
  @Query("SELECT * FROM Podcast ORDER BY FeedTitle")
  fun loadPodcasts(): LiveData<List<Podcast>>

  @Query("SELECT * FROM Podcast ORDER BY FeedTitle")
  suspend fun loadPodcastsStatic(): List<Podcast>

  @Query("SELECT * FROM Episode WHERE podcastId = :podcastId ORDER BY releaseDate DESC")
  suspend fun loadEpisodes(podcastId: Long): List<Episode>

  @Query("SELECT * FROM Podcast WHERE feedUrl = :url")
  suspend fun loadPodcast(url: String): Podcast?

  @Insert(onConflict = REPLACE)
  suspend fun insertPodcast(podcast: Podcast): Long

  @Insert(onConflict = REPLACE)
  suspend fun insertEpisode(episode: Episode): Long

  @Delete
  suspend fun deletePodcast(podcast: Podcast)
}
