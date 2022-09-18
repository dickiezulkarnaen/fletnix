/***
 * Author         : Dicky Zulkarnain
 * Date           : 18/09/22
 * Original File  : SeeAllAdapter
 ***/

package com.dickiez.fletnix.features.see_all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dickiez.fletnix.core.constants.BackDropSize
import com.dickiez.fletnix.core.constants.Constant
import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.core.data.models.ContentResult
import com.dickiez.fletnix.databinding.ItemLoadingBinding
import com.dickiez.fletnix.databinding.ItemSeeAllContentBinding
import com.dickiez.fletnix.widgets.InfinityScrollAdapter


class SeeAllAdapter(private val mediaType: MediaType) : InfinityScrollAdapter<ContentResult, RecyclerView.ViewHolder>(Diff) {

  companion object {
    const val VIEW_TYPE_ITEM = 1
    const val VIEW_TYPE_LOADING = 0
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return if (viewType == VIEW_TYPE_ITEM) {
      val binding = ItemSeeAllContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      Holder(binding)
    } else {
      val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      LoadingHolder(binding.root)
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (holder is Holder) {
      getItem(position)?.let { holder.bind(it) }
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (isIndexLoadingMore(position)) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
  }

  inner class Holder(private val binding: ItemSeeAllContentBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ContentResult) {
      binding.textView.text = item.name ?: item.title ?: item.originalTitle ?: item.originalName
      val image = "${Constant.TMDB_IMAGE_BASE_URL}/${BackDropSize.W_780.value}${item.backdropPath}"
      Glide.with(binding.root.context)
        .load(image)
        .into(binding.imageView)
      binding.root.setOnClickListener {
        onItemClick(item)
      }
    }
  }

  object Diff : DiffUtil.ItemCallback<ContentResult>() {
    override fun areItemsTheSame(oldItem: ContentResult, newItem: ContentResult): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContentResult, newItem: ContentResult): Boolean {
      return oldItem == newItem
    }

  }

}