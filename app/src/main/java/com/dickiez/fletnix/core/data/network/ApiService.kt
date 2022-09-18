/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : ApiService
 ***/

package com.dickiez.fletnix.core.data.network

import retrofit2.Response


class ApiService {
  companion object {
    suspend fun <T> call(result: (ApiResult<T>) -> Unit, apiCall: suspend () -> Response<T>?) {
      result(ApiResult(ApiState.Loading, null))
      try {
        val res = apiCall.invoke()
        if (res != null) {
          result(ApiResult(ApiState.Finish, ApiResponse(res)))
        } else {
          result(ApiResult(ApiState.Finish, null))
        }
      } catch (throwable: Throwable) {
        throwable.printStackTrace()
        result(ApiResult(ApiState.Error, ApiResponse(throwable)))
      }
    }
  }
}