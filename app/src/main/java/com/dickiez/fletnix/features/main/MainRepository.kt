/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : MainRepository
 ***/

package com.dickiez.fletnix.features.main

import com.dickiez.fletnix.core.constants.Constant
import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.core.constants.TimeWindow
import com.dickiez.fletnix.core.data.models.ContentResponse
import com.dickiez.fletnix.core.data.network.Api
import com.dickiez.fletnix.core.data.network.ApiResult
import com.dickiez.fletnix.core.data.network.ApiService
import javax.inject.Inject


class MainRepository @Inject constructor(private val api: Api) {

  suspend fun getTrending(
    mediaType: MediaType,
    timeWindow: TimeWindow,
    result: (ApiResult<ContentResponse>) -> Unit
  ) = ApiService.call(result) {
    api.getTrending(
      apiKey = Constant.TMDB_API_KEY,
      mediaType = mediaType.value,
      timeWindow = timeWindow.name.lowercase()
    )
  }

  suspend fun getDiscoverMovies(
    result: (ApiResult<ContentResponse>) -> Unit
  ) = ApiService.call(result) {
    api.getDiscoverMovies(
      apiKey = Constant.TMDB_API_KEY
    )
  }
  suspend fun getDiscoverTVShows(
    result: (ApiResult<ContentResponse>) -> Unit
  ) = ApiService.call(result) {
    api.getDiscoverTVShows(
      apiKey = Constant.TMDB_API_KEY
    )
  }

  suspend fun getTopRatedMovies(
    result: (ApiResult<ContentResponse>) -> Unit
  ) = ApiService.call(result) {
    api.getPopularMovies(apiKey = Constant.TMDB_API_KEY)
  }

  suspend fun getTopRatedTVShows(
    result: (ApiResult<ContentResponse>) -> Unit)
  = ApiService.call(result) {
    api.getPopularTVShows(apiKey = Constant.TMDB_API_KEY)
  }
}