/***
 * Author         : Dicky Zulkarnain
 * Date           : 18/09/22
 * Original File  : DetailContentRepository
 ***/

package com.dickiez.fletnix.features.main.content_detail

import com.dickiez.fletnix.core.constants.Constant
import com.dickiez.fletnix.core.data.models.DetailContent
import com.dickiez.fletnix.core.data.network.Api
import com.dickiez.fletnix.core.data.network.ApiResult
import com.dickiez.fletnix.core.data.network.ApiService
import javax.inject.Inject


class DetailContentRepository @Inject constructor(private val api : Api) {

  suspend fun getDetailMovie(movieId : Int, result: (ApiResult<DetailContent>) -> Unit) = ApiService.call(result) {
    api.getDetailMovie(movieId, Constant.TMDB_API_KEY)
  }

  suspend fun getDetailTVShow(tvId : Int, result: (ApiResult<DetailContent>) -> Unit) = ApiService.call(result) {
    api.getDetailTVShows(tvId, Constant.TMDB_API_KEY)
  }

}