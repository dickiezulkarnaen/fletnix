/***
 * Author         : Dicky Zulkarnain
 * Date           : 15/09/22
 * Original File  : CarouselViewPager
 ***/

package com.dickiez.fletnix.features.main.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dickiez.fletnix.core.constants.BackDropSize
import com.dickiez.fletnix.core.constants.Constant
import com.dickiez.fletnix.core.data.models.Banner
import com.dickiez.fletnix.databinding.ItemCarouselBinding
import com.dickiez.fletnix.utils.Tools


class CarouselViewAdapter(private val items: ArrayList<Banner>) :
  RecyclerView.Adapter<CarouselViewAdapter.Holder>() {

  class Holder(private val binding: ItemCarouselBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Banner) {
      binding.textView.text = item.title
      val image = "${Constant.TMDB_IMAGE_BASE_URL}/${BackDropSize.W_780.value}${item.image}"
      Glide.with(binding.root.context)
        .load(image)
        .into(binding.imageView)
      binding.root.setOnClickListener {
        Tools.debug("CLICK", item.title.toString())
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
    val binding = ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return Holder(binding)
  }

  override fun onBindViewHolder(holder: Holder, position: Int) {
    holder.bind(items[position])
  }

  override fun getItemCount(): Int = items.size
}