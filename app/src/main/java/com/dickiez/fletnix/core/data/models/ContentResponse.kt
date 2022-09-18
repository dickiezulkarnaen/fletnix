/***
 * Author         : Dicky Zulkarnain
 * Date           : 17/09/22
 * Original File  : ContentResponse
 ***/

package com.dickiez.fletnix.core.data.models

import com.google.gson.annotations.SerializedName


data class ContentResponse(
  @SerializedName("page")
  var page: Int? = null,
  @SerializedName("results")
  var results: List<ContentResult?>? = null,
  @SerializedName("total_pages")
  var totalPages: Int? = null,
  @SerializedName("total_results")
  var totalResults: Int? = null
)