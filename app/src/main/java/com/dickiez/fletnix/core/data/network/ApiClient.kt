/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : ApiClient
 ***/

package com.dickiez.fletnix.core.data.network

import com.dickiez.fletnix.BuildConfig
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


object ApiClient {

  private fun baseHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
      val original = chain.request()
      val request = original.newBuilder().apply {
        header("User-Agent", System.getProperty("http.agent") ?: "base")
        header("Accept", "application/json")
        method(original.method, original.body)
      }.build()
      chain.proceed(request)
    }
  }

  private fun cacheInterceptor(): Interceptor {
    return Interceptor { chain ->
      val response = chain.proceed(chain.request())
      val cacheControl = CacheControl.Builder()
        .maxAge(15, TimeUnit.SECONDS)
        .build()
      response.newBuilder()
        .header("Cache-Control", cacheControl.toString())
        .build()
    }
  }

  private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level =
      if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    return logging
  }

  val builder: OkHttpClient.Builder
    get() = OkHttpClient.Builder()
      .addInterceptor(baseHeaderInterceptor())
      .addInterceptor(httpLoggingInterceptor())
      .addNetworkInterceptor(cacheInterceptor())
      .writeTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .connectTimeout(30, TimeUnit.SECONDS)

}