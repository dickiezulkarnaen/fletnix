/***
 * Author         : Dicky Zulkarnain
 * Date           : 18/09/22
 * Original File  : DetailContentVIewModel
 ***/

package com.dickiez.fletnix.features.main.content_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.core.data.models.DetailContent
import com.dickiez.fletnix.core.data.network.ApiState
import com.dickiez.fletnix.core.lifecycle.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailContentViewModel @Inject constructor(private val repository: DetailContentRepository) : BaseViewModel() {

  private var contentId: Int? = null
  private var mediaType: MediaType = MediaType.MOVIE // DEFAULT VALUE

  private val _contentDetail = MutableLiveData<DetailContent>()
  val contentDetail : LiveData<DetailContent> = _contentDetail

  // TODO: CREATE VIEW_MODEL FACTORY INSTEAD OF THIS
  fun initContentAndGetData(id: Int, mediaType: MediaType) {
    this.contentId = id
    this.mediaType = mediaType
    getDetail()
  }

  private fun getDetail() {
    when (mediaType) {
      MediaType.MOVIE -> getDetailMovie()
      MediaType.TV_SHOW -> getDetailTVShow()
    }
  }

  private fun getDetailMovie() = viewModelScope.launch {
    repository.getDetailMovie(contentId ?: return@launch) {
      if (it.state == ApiState.Finish && it.response?.isSuccessful == true) {
        _contentDetail.value = it.response.body
      }
    }
  }

  private fun getDetailTVShow() = viewModelScope.launch {
    repository.getDetailTVShow(contentId ?: return@launch) {
      if (it.state == ApiState.Finish && it.response?.isSuccessful == true) {
        _contentDetail.value = it.response.body
      }
    }
  }

}