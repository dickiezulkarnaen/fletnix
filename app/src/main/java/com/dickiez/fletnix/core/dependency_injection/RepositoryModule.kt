/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : RepositoryModule
 ***/

package com.dickiez.fletnix.core.dependency_injection

import com.dickiez.fletnix.core.data.network.Api
import com.dickiez.fletnix.features.main.MainRepository
import com.dickiez.fletnix.features.see_all.SeeAllRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

  @ViewModelScoped
  @Provides
  fun provideMainRepository(api: Api): MainRepository = MainRepository(api)

  @ViewModelScoped
  @Provides
  fun provideSeeAllRepository(api: Api): SeeAllRepository = SeeAllRepository(api)

}