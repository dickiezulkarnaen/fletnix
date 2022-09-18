/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : PreferenceHelper
 ***/

package com.dickiez.fletnix.core.data.local

import android.content.Context

class PreferenceHelper(private val context: Context) {

  private val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)


  companion object {
    private const val PREFERENCE_NAME = "FLETNIX_PREFERENCES"
  }
}