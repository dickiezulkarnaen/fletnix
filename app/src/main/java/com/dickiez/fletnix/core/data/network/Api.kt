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
    @Query("page") page: Int = 1,
    @Query("api_key") apiKey: String,
  ): Response<ContentResponse>

  @GET("discover/movie")
  suspend fun getDiscoverMovies(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int = 1,
  ): Response<ContentResponse>

  @GET("discover/tv")
  suspend fun getDiscoverTVShows(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int = 1,
  ): Response<ContentResponse>

  @GET("movie/popular")
  suspend fun getPopularMovies(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int = 1,
  ): Response<ContentResponse>

  @GET("tv/popular")
  suspend fun getPopularTVShows(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int = 1,
  ): Response<ContentResponse>

}