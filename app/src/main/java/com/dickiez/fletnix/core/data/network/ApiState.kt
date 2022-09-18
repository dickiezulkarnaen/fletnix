/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : ApiState
 ***/

package com.dickiez.fletnix.core.data.network


sealed class ApiState {
  object Loading : ApiState()
  object Finish : ApiState()
  object Success : ApiState()
  object Error : ApiState()
}