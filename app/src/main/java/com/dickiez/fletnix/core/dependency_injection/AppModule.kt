/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : AppModule
 ***/

package com.dickiez.fletnix.core.dependency_injection

import android.content.Context
import com.dickiez.fletnix.App
import com.dickiez.fletnix.core.data.local.PreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Singleton
  @Provides
  fun provideApp(@ApplicationContext app: Context): App = app as App

  @Singleton
  @Provides
  fun providePreferencesHelper(app: App): PreferenceHelper =
    PreferenceHelper(app.applicationContext)
}