/***
 * Author         : Dicky Zulkarnain
 * Date           : 17/09/22
 * Original File  : ContentAdapter
 ***/

package com.dickiez.fletnix.features.main.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dickiez.fletnix.core.constants.BackDropSize
import com.dickiez.fletnix.core.constants.Constant
import com.dickiez.fletnix.core.data.models.Banner
import com.dickiez.fletnix.databinding.ItemContentSectionBinding
import com.dickiez.fletnix.databinding.ItemLoadingBinding
import com.dickiez.fletnix.widgets.InfinityScrollAdapter


class ContentSectionAdapter : InfinityScrollAdapter<Banner, RecyclerView.ViewHolder>(Diff()) {

  companion object {
    const val VIEW_TYPE_ITEM = 1
    const val VIEW_TYPE_LOADING = 0
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return if (viewType == VIEW_TYPE_ITEM) {
      val binding = ItemContentSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      Holder(binding)
    } else {
      val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      LoadingHolder(binding.root)
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (isIndexLoadingMore(position)) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (holder is Holder) getItem(position)?.let { holder.bind(it) }
  }

  inner class Holder(private val binding : ItemContentSectionBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Banner) {
      val image = "${Constant.TMDB_IMAGE_BASE_URL}/${BackDropSize.W_300.value}${item.image}"
      Glide.with(binding.imageView.context)
        .load(image)
        .into(binding.imageView)
      binding.root.setOnClickListener { onItemClick(item) }
    }
  }

  class Diff : DiffUtil.ItemCallback<Banner>() {
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
      return oldItem == newItem
    }

  }

}