/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : Api
 ***/

package com.dickiez.fletnix.core.data.network

import com.dickiez.fletnix.core.data.models.ContentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {

  @GET("trending/{media_type}/{time_window}")
  suspend fun getTrending(
    @Path("media_type") mediaType: String,
    @Path("time_window") timeWindow: String,
    @Query("api_key") apiKey: String,
  ): Response<ContentResponse>

  @GET("discover/movie")
  suspend fun getDiscoverMovies(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int,
  ): Response<ContentResponse>

  @GET("discover/tv")
  suspend fun getDiscoverTVShows(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int,
  ): Response<ContentResponse>

  @GET("movie/top_rated")
  suspend fun getTopRatedMovies(
    @Query("api_key") apiKey: String,
  ): Response<ContentResponse>

  @GET("tv/top_rated")
  suspend fun getTopRatedTVShows(
    @Query("api_key") apiKey: String,
  ): Response<ContentResponse>

}