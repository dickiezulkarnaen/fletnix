/***
 * Author         : Dicky Zulkarnain
 * Date           : 18/09/22
 * Original File  : SeeAllRepository
 ***/

package com.dickiez.fletnix.features.see_all

import com.dickiez.fletnix.core.constants.Constant
import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.core.constants.TimeWindow
import com.dickiez.fletnix.core.data.models.ContentResponse
import com.dickiez.fletnix.core.data.network.Api
import com.dickiez.fletnix.core.data.network.ApiResult
import com.dickiez.fletnix.core.data.network.ApiService
import javax.inject.Inject


class SeeAllRepository @Inject constructor(private val api: Api) {

  suspend fun getAllDiscoverMovies(
    page: Int,
    result: (ApiResult<ContentResponse>) -> Unit) = ApiService.call(result) {
    api.getDiscoverMovies(
      apiKey = Constant.TMDB_API_KEY,
      page = page
    )
  }

  suspend fun getAllDiscoverTVShows(
    page: Int,
    result: (ApiResult<ContentResponse>) -> Unit) = ApiService.call(result) {
    api.getDiscoverTVShows(
      apiKey = Constant.TMDB_API_KEY,
      page = page
    )
  }

  suspend fun getAllTrending(
    mediaType: MediaType,
    timeWindow: TimeWindow,
    page: Int,
    result: (ApiResult<ContentResponse>) -> Unit) = ApiService.call(result) {
    api.getTrending(
      apiKey = Constant.TMDB_API_KEY,
      mediaType = mediaType.value,
      timeWindow = timeWindow.value,
      page = page
    )
  }

}