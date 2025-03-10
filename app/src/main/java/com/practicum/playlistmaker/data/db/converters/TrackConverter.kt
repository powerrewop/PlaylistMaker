package com.practicum.playlistmaker.data.db.converters

import com.practicum.playlistmaker.data.db.entity.FavTrackEntity
import com.practicum.playlistmaker.data.db.entity.ListsEntity
import com.practicum.playlistmaker.data.db.entity.TracksLists
import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.domain.model.PlayListTrack
import com.practicum.playlistmaker.domain.model.Track

class TrackConverter {

    fun listFavTrackEntityToListTrack(favTrack: List<FavTrackEntity>): List<Track>?{

        return favTrack.map { Track(
            it.trackName,
            it.artistName,
            it.trackTime,
            it.artworkUrl100,
            it.trackId,
            false,
            it.collectionName,
            it.releaseDate,
            it.primaryGenreName,
            it.country,
            it.previewUrl,
            true
        ) }
    }

    fun trackToFavTrackEntity(track: Track): FavTrackEntity{

        return FavTrackEntity(
            track.trackName,
            track.artistName,
            track.trackTime,
            track.artworkUrl100,
            track.trackId,
            false,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            System.currentTimeMillis())
    }

    fun playListToListsEntity(playList: PlayList): ListsEntity{

        return ListsEntity(
            playList.id,
            playList.name,
            playList.image,
            playList.desc
        )
    }

    fun playListTrackToTracksLists(playListTrack: PlayListTrack): TracksLists{

        return TracksLists(
            playListTrack.id,
            playListTrack.trackId,
            playListTrack.listId,
            playListTrack.trackName,
            playListTrack.artistName,
            playListTrack.trackTime,
            playListTrack.artworkUrl100,
            false,
            playListTrack.collectionName,
            playListTrack.releaseDate,
            playListTrack.primaryGenreName,
            playListTrack.country,
            playListTrack.previewUrl
        )
    }

    fun listListEntityToListPlayList(listsEntity: List<ListsEntity>): List<PlayList>{

        return listsEntity.map {PlayList(
            it.id,
            it.name,
            it.image,
            it.desc,
            ""
            )
        }
    }

    fun listTrackListEntityToPlayListTrack(trackLists: List<TracksLists>): List<PlayListTrack>{

        return trackLists.map { PlayListTrack(
            it.id,
            it.trackId,
            it.listId,
            it.trackName,
            it.artistName,
            it.trackTime,
            it.artworkUrl100,
            false,
            it.collectionName,
            it.releaseDate,
            it.primaryGenreName,
            it.country,
            it.previewUrl
        )
        }
    }
}