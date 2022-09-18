/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : ApiResult
 ***/

package com.dickiez.fletnix.core.data.network


data class ApiResult<T>(
  val state: ApiState,
  val response: ApiResponse<T>?
)