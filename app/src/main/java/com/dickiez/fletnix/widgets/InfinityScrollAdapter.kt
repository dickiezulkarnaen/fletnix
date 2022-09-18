/***
 * Author         : Dicky Zulkarnain
 * Date           : 17/09/22
 * Original File  : InfinityScrollAdapter
 ***/

package com.dickiez.fletnix.widgets

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.*

abstract class InfinityScrollAdapter<T, VH : RecyclerView.ViewHolder> constructor(
  diffCallback: DiffUtil.ItemCallback<T>
) : RecyclerView.Adapter<VH>() {

  private val differ: AsyncListDiffer<T>
  private var data : ArrayList<AdapterItem<T>> = arrayListOf()
  private var recyclerView : RecyclerView? = null
  private var loadMoreListener : OnLoadMoreListener? = null
  private var loadMoreIndex : Int? = null

  protected var onItemClick : (T) -> Unit = {}

  init {
    differ = AsyncListDiffer(
      AdapterListUpdateCallback(this),
      AsyncDifferConfig.Builder(diffCallback).build()
    )
  }

  fun isIndexLoadingMore(index: Int): Boolean {
    return if (loadMoreIndex != null) {
      val item = data[index]
      item.viewType == ViewType.LOAD_MORE
    } else false
  }

  override fun getItemCount(): Int = data.size

  @SuppressLint("NotifyDataSetChanged")
  fun setData(data : ArrayList<T>) {
    this.data = data.map { AdapterItem(viewType = ViewType.DATA_ITEM, it) } as ArrayList
    this.differ.submitList(this.data.map { it.item })
    notifyDataSetChanged()
    loadMoreListener?.stopLoading()
  }

  @SuppressLint("NotifyDataSetChanged")
  fun addAll(data: ArrayList<T>) {
    this.data.addAll(data.map { AdapterItem(ViewType.DATA_ITEM, it) })
    notifyDataSetChanged()
    loadMoreListener?.stopLoading()
  }

  fun addItem(item: T) {
    val lastIndex = data.lastIndex
    this.data.add(AdapterItem(ViewType.DATA_ITEM, item))
    notifyItemInserted(lastIndex)
  }

  fun addOnItemClick(onClick : (T) -> Unit) {
    onItemClick = onClick
  }

  fun addLoadMoreListener(
    visibleThreshold: Int = 10,
    direction: OnLoadMoreListener.Direction = OnLoadMoreListener.Direction.DOWN,
    callback: () -> Unit
  ) {
    if (recyclerView?.layoutManager != null) {
      loadMoreListener = OnLoadMoreListener(
        recyclerView?.layoutManager!!,
        visibleThreshold,
        direction,
        callback
      )
      recyclerView?.addOnScrollListener(loadMoreListener!!)
    } else throw Exception("Please attach RecyclerView and it's LayoutManager first to use LoadMoreListener")
  }

  fun getData() : List<T?> = data.map { it.item }

  fun getItem(position: Int) : T? {
    if (data.size < position) return null
    return data[position].item
  }

  @SuppressLint("NotifyDataSetChanged")
  fun setIsLoadingMore(value : Boolean) {
    if (value) addLoadingItem() else removeLoadingItem()
    notifyDataSetChanged()
  }

  private fun addLoadingItem() {
    if (loadMoreIndex == null) data.add(AdapterItem(ViewType.LOAD_MORE, null))
    loadMoreIndex = data.lastIndex
  }

  private fun removeLoadingItem() {
    loadMoreIndex?.let { index ->
      data.removeAt(data.indexOfLast { it.viewType == ViewType.LOAD_MORE })
      notifyItemRemoved(index)
      loadMoreIndex = null
    }
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    this.recyclerView = recyclerView
  }

  /* Support Classes */
  class LoadingHolder(view : View) : RecyclerView.ViewHolder(view)
  class AdapterItem<T>(var viewType: ViewType, var item: T?)
  enum class ViewType {
    LOAD_MORE,
    DATA_ITEM,
  }

}
