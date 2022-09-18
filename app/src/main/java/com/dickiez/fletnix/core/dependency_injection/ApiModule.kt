/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : ApiModule
 ***/

package com.dickiez.fletnix.core.dependency_injection

import com.dickiez.fletnix.core.constants.Constant
import com.dickiez.fletnix.core.data.network.Api
import com.dickiez.fletnix.core.data.network.ApiClient
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

  @Singleton
  @Provides
  fun provideApi(): Api = Retrofit.Builder()
    .baseUrl(Constant.BASE_URL)
    .client(ApiClient.builder.build())
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    .build().create(Api::class.java)

}