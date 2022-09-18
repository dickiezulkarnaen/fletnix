/***
 * Author         : Dicky Zulkarnain
 * Date           : 12/09/22
 * Original File  : MainViewModel
 ***/

package com.dickiez.fletnix.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dickiez.fletnix.core.constants.BackDropSize
import com.dickiez.fletnix.core.constants.Constant
import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.core.constants.TimeWindow
import com.dickiez.fletnix.core.data.models.Banner
import com.dickiez.fletnix.core.data.models.ContentResult
import com.dickiez.fletnix.core.data.network.ApiState
import com.dickiez.fletnix.core.lifecycle.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {

  private val _showSplashScreen = MutableLiveData(false)
  val showSplashScreen: LiveData<Boolean> get() = _showSplashScreen

  private val _movieBanners = MutableLiveData<ArrayList<Banner>>(arrayListOf())
  val movieBanners: LiveData<ArrayList<Banner>> get() = _movieBanners

  private val _tvBanners = MutableLiveData<ArrayList<Banner>>(arrayListOf())
  val tvBanners: LiveData<ArrayList<Banner>> get() = _tvBanners

  private val _movieTrending = MutableLiveData<ArrayList<Banner>>(arrayListOf())
  val movieTrending: LiveData<ArrayList<Banner>> get() = _movieTrending

  private val _movieDiscover = MutableLiveData<ArrayList<Banner>>(arrayListOf())
  val movieDiscover: LiveData<ArrayList<Banner>> get() = _movieDiscover

  private val _tvTrending = MutableLiveData<ArrayList<Banner>>(arrayListOf())
  val tvTrending: LiveData<ArrayList<Banner>> get() = _tvTrending

  private val _tvDiscover = MutableLiveData<ArrayList<Banner>>(arrayListOf())
  val tvDiscover: LiveData<ArrayList<Banner>> get() = _tvDiscover

  val tabTitles = arrayOf("Movies", "TV Shows")

  init {
    getTopRatedMovies()
    getTopRatedTVShows()
    getTrendingTVShows()
    getTrendingMovies()
    getDiscoverMovies()
    getDiscoverTVShows()
  }

  private fun getTopRatedMovies() = viewModelScope.launch {
    repository.getTopRatedMovies { result ->
      if (result.state == ApiState.Finish) {
        if (result.response != null) {
          _movieBanners.value = mapBackdropPath(MediaType.MOVIE, result.response.body?.results)
        }
      }
    }
  }

  private fun getTopRatedTVShows() = viewModelScope.launch {
    repository.getTopRatedTVShows { result ->
      if (result.state == ApiState.Finish) {
        if (result.response != null) {
          _tvBanners.value = mapBackdropPath(MediaType.TV_SHOW, result.response.body?.results)
        }
      }
    }
  }

  private fun getTrendingMovies() = viewModelScope.launch {
    repository.getTrending(MediaType.MOVIE, TimeWindow.WEEK) { result ->
      setIsLoading(result.state == ApiState.Loading)
      if (result.state == ApiState.Finish) {
        if (result.response != null) {
          _movieTrending.value = mapPosterPath(MediaType.MOVIE, result.response.body?.results)
        }
      }
    }
  }

  private fun getTrendingTVShows() = viewModelScope.launch {
    repository.getTrending(MediaType.TV_SHOW, TimeWindow.WEEK) { result ->
      setIsLoading(result.state == ApiState.Loading)
      if (result.state == ApiState.Finish) {
        if (result.response != null) {
          _tvTrending.value = mapPosterPath(MediaType.TV_SHOW, result.response.body?.results)
        }
      }
    }
  }

  private fun getDiscoverMovies() = viewModelScope.launch {
    repository.getDiscoverMovies { result ->
      setIsLoading(result.state == ApiState.Loading)
      if (result.state == ApiState.Finish) {
        if (result.response != null) {
          _movieDiscover.value = mapPosterPath(MediaType.MOVIE, result.response.body?.results)
        }
      }
    }
  }

  private fun getDiscoverTVShows() = viewModelScope.launch {
    repository.getDiscoverTVShows{ result ->
      setIsLoading(result.state == ApiState.Loading)
      if (result.state == ApiState.Finish) {
        if (result.response != null) {
          _tvDiscover.value = mapPosterPath(MediaType.TV_SHOW, result.response.body?.results)
        }
      }
    }
  }

  private fun mapBackdropPath(mediaType: MediaType, results: List<ContentResult?>?): ArrayList<Banner> {
    val arrayList = arrayListOf<Banner>()
    results?.forEach { result ->
      result?.let {
        val title = when(mediaType) {
          MediaType.MOVIE -> it.originalTitle
          MediaType.TV_SHOW -> it.originalName
        }
        arrayList.add(Banner(id = it.id, title = title, image = it.backdropPath))
      }
    }
    return arrayList
  }


  private fun mapPosterPath(mediaType: MediaType, results: List<ContentResult?>?): ArrayList<Banner> {
    val arrayList = arrayListOf<Banner>()
    results?.forEach { result ->
      result?.let {
        val title = when(mediaType) {
          MediaType.MOVIE -> it.title
          MediaType.TV_SHOW -> it.name
          else -> "-"
        }
        arrayList.add(Banner(id = it.id, title = title, image = it.posterPath))
      }
    }
    return arrayList
  }

}