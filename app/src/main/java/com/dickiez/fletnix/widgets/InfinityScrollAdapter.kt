/***
 * Author         : Dicky Zulkarnain
 * Date           : 17/09/22
 * Original File  : InfinityScrollAdapter
 ***/

package com.dickiez.fletnix.widgets

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.*

abstract class InfinityScrollAdapter<T, VH : RecyclerView.ViewHolder> protected constructor(
  diffCallback: DiffUtil.ItemCallback<T>
) : RecyclerView.Adapter<VH>() {

  private val differ: AsyncListDiffer<T>
  private var data : ArrayList<AdapterItem<T>> = arrayListOf()
  private var recyclerView : RecyclerView? = null
  private var loadMoreListener : OnLoadMoreListener? = null

  protected var onItemClick : (T) -> Unit = {}

  @SuppressLint("NotifyDataSetChanged")
  fun setData(data : ArrayList<T>) {
    this.data = data.map { AdapterItem(viewType = ViewType.DATA_ITEM, it) } as ArrayList
    this.differ.submitList(this.data.map { it.item })
    notifyDataSetChanged()
    loadMoreListener?.stopLoading()
  }

  fun addAll(data: ArrayList<T>) {
    this.data.addAll(data.map { AdapterItem(ViewType.DATA_ITEM, it) })
    notifyItemInserted(data.lastIndex)
    loadMoreListener?.stopLoading()
  }

  fun addItem(item: T) {
    this.data.add(AdapterItem(ViewType.DATA_ITEM, item))
    notifyItemInserted(data.lastIndex)
  }

  fun addOnItemClick(onClick : (T) -> Unit) {
    onItemClick = onClick
  }

  fun addLoadMoreListener(
    layoutManager: RecyclerView.LayoutManager,
    visibleThreshold: Int = 10,
    scrollDirection: OnLoadMoreListener.Direction = OnLoadMoreListener.Direction.DOWN,
    onLoadMore: () -> Unit
  ) {
    recyclerView?.addOnScrollListener(
      OnLoadMoreListener(
        layoutManager,
        visibleThreshold,
        scrollDirection,
        onLoadMore
      )
    )
  }

  fun getData() : List<T?> = data.map { it.item }

  fun getItem(position: Int) : T? {
    if (data.size < position) return null
    return data[position].item
  }

  fun setIsLoadingMore(isLoadingMore : Boolean) {
    if (isLoadingMore) {
      data.add(AdapterItem(ViewType.LOAD_MORE, null))
    } else {
      data.removeAt(data.indexOfLast { it.viewType == ViewType.LOAD_MORE })
    }
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    this.recyclerView = recyclerView
  }

  override fun getItemCount(): Int = data.size

  val isLoadingMore: Boolean get() = data.last().viewType == ViewType.LOAD_MORE

  class LoadingHolder(view : View) : RecyclerView.ViewHolder(view)

  class AdapterItem<T>(var viewType: ViewType, var item: T?)

  enum class ViewType {
    LOAD_MORE,
    DATA_ITEM,
  }

  init {
    differ = AsyncListDiffer(
      AdapterListUpdateCallback(this),
      AsyncDifferConfig.Builder(diffCallback).build()
    )
  }

}
