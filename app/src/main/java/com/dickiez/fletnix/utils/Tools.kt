package com.dickiez.fletnix.utils

import android.util.Log
import com.dickiez.fletnix.BuildConfig

object Tools {
  fun debug(tag: String, message: String) {
    if (BuildConfig.DEBUG) Log.d(tag, "---> $message")
  }
}