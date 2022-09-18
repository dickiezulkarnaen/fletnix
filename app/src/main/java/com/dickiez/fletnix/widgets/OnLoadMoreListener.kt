/***
 * Author         : Dicky Zulkarnain
 * Date           : 17/09/22
 * Original File  : OnLoadMoreListener
 ***/

package com.dickiez.fletnix.widgets

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class OnLoadMoreListener(
  private val layoutManager: RecyclerView.LayoutManager,
  private val visibleThreshold : Int = 5,
  private val scrollDirection : Direction = Direction.DOWN,
  private val listener: () -> Unit
) : RecyclerView.OnScrollListener() {
  private var lastVisibleItem: Int = 0
  private var totalItemCount: Int = 0
  private var loading : Boolean = false

  private fun isScrolling(dy: Int) : Boolean {
    return when (scrollDirection) {
      Direction.DOWN -> dy > 0
      Direction.UP -> dy < 0
    }
  }

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)
    totalItemCount = layoutManager.itemCount
    lastVisibleItem = when (layoutManager) {
      is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
      is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
      else -> 0
    }
    if (isScrolling(dy)) {
      if (!loading && totalItemCount <= lastVisibleItem+visibleThreshold) {
        if (totalItemCount > 0) listener()
        loading = true
      }
    }
  }

  fun stopLoading() { loading = false }

  enum class Direction { UP, DOWN }
}