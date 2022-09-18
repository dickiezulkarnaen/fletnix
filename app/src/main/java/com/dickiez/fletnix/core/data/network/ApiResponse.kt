/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : ApiResponse
 ***/

package com.dickiez.fletnix.core.data.network

import retrofit2.Response
import java.io.IOException


class ApiResponse<T> {
  val code: Int
  val body: T?
  val error: String?

  val isSuccessful: Boolean get() = code in 200..300

  constructor(error: Throwable) {
    this.code = 500
    this.body = null
    this.error = error.message
  }

  constructor(response: Response<T>) {
    this.code = response.code()
    if (response.isSuccessful) {
      this.body = response.body()
      this.error = null
    } else {
      var errorMessage: String? = null
      response.errorBody()?.let {
        try {
          errorMessage = response.errorBody()?.string()
        } catch (e: IOException) {
          e.printStackTrace()
        }
      }

      errorMessage?.apply {
        if (isNullOrEmpty() || trim { it <= ' ' }.isEmpty()) {
          errorMessage = response.message()
        }
      }
      this.body = null
      this.error = errorMessage ?: "Ups, something wrong!"
    }
  }
}