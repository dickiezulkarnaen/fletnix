/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : App
 ***/

package com.dickiez.fletnix

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

  override fun onCreate() {
    super.onCreate()
  }
}