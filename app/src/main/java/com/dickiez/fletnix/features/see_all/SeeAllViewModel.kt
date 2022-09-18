/***
 * Author         : Dicky Zulkarnain
 * Date           : 18/09/22
 * Original File  : SeeAllViewModel
 ***/

package com.dickiez.fletnix.features.see_all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dickiez.fletnix.core.constants.ContentSection
import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.core.constants.TimeWindow
import com.dickiez.fletnix.core.data.models.ContentResult
import com.dickiez.fletnix.core.data.network.ApiState
import com.dickiez.fletnix.core.lifecycle.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(private val repository: SeeAllRepository) : BaseViewModel() {

  private val _seeAllData = MutableLiveData<ArrayList<ContentResult>>()
  val seeAllData :LiveData<ArrayList<ContentResult>> = _seeAllData

  private var _page = 0
  val page : Int get() = _page
  private var _lastPage = 0
  val lastPage : Int get() = _lastPage

  private val _isLoadingMore = MutableLiveData(false)
  val isLoadingMore : LiveData<Boolean> get() = _isLoadingMore

  private var _mediaType = MediaType.MOVIE // DEFAULT VALUE

  private var _contentSection = ContentSection.DISCOVER // DEFAULT VALUE

  fun setMediaType(type: MediaType) { _mediaType = type }

  fun setContentSection(section: ContentSection) { _contentSection = section }

  fun getAllData() = viewModelScope.launch {
    _page++
    when (_contentSection) {
      ContentSection.DISCOVER -> {
        when (_mediaType) {
          MediaType.MOVIE -> getDiscoverMovies()
          MediaType.TV_SHOW -> getDiscoverTVShows()
        }
      }
      ContentSection.TRENDING -> getTrending()
    }
  }

  private fun getDiscoverMovies() = viewModelScope.launch {
    repository.getAllDiscoverMovies(_page) {
      if (_page > 1) _isLoadingMore.value = it.state == ApiState.Loading
      if (it.state == ApiState.Finish) {
        if (it.response?.isSuccessful == true) {
          _seeAllData.value = applyData(it.response.body?.results)
          _page = it.response.body?.page ?: _page
          _lastPage = it.response.body?.totalPages ?: _lastPage
        }
      }
    }
  }

  private fun getDiscoverTVShows() = viewModelScope.launch {
    repository.getAllDiscoverTVShows(_page) {
      if (_page > 1) _isLoadingMore.value = it.state == ApiState.Loading
      if (it.state == ApiState.Finish) {
        if (it.response?.isSuccessful == true) {
          _seeAllData.value = applyData(it.response.body?.results)
          _page = it.response.body?.page ?: _page
          _lastPage = it.response.body?.totalPages ?: _lastPage
        }
      }
    }
  }

  private fun getTrending() = viewModelScope.launch {
    repository.getAllTrending(_mediaType, TimeWindow.WEEK, _page) {
      if (_page > 1) _isLoadingMore.value = it.state == ApiState.Loading
      if (it.state == ApiState.Finish) {
        if (it.response?.isSuccessful == true) {
          _seeAllData.value = applyData(it.response.body?.results)
          _page = it.response.body?.page ?: _page
          _lastPage = it.response.body?.totalPages ?: _lastPage
        }
      }
    }
  }

  private fun applyData(results: List<ContentResult?>?) : ArrayList<ContentResult> {
    val data = arrayListOf<ContentResult>()
    results?.forEach { result ->
      result?.let { data.add(it) }
    }
    return data
  }

}