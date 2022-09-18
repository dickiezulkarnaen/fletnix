package com.dickiez.fletnix.features.see_all

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.dickiez.fletnix.core.base.BaseActivity
import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.databinding.ActivitySeeAllBinding
import com.dickiez.fletnix.utils.Tools
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllActivity : BaseActivity() {

  private lateinit var binding: ActivitySeeAllBinding
  private lateinit var viewModel: SeeAllViewModel

  private lateinit var mediaType: MediaType
  private lateinit var adapter: SeeAllAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySeeAllBinding.inflate(layoutInflater)
    setContentView(binding.root)
    viewModel = ViewModelProvider(this)[SeeAllViewModel::class.java]
    mediaType = MediaType.valueOf(intent.getStringExtra(MEDIA_TYPE)!!)
    setupRecyclerView()
    viewModel.getAllData()

    viewModel.seeAllData.observe(this) {
      if (it.isNotEmpty()) {
        if (viewModel.page > 1) {
          adapter.addAll(it)
          adapter.setIsLoadingMore(false)
        } else {
          adapter.setData(it)
        }
      }
    }
  }

  private fun setupRecyclerView() {
    adapter = SeeAllAdapter(mediaType)
    binding.recyclerViewSeeAll.adapter = adapter
    adapter.addOnItemClick {
      Tools.debug("ITEM CLICK", it.id.toString())
    }
    binding.recyclerViewSeeAll.layoutManager?.let {
      adapter.addLoadMoreListener(it) {
        Tools.debug("PAGE", viewModel.page.toString())
        Tools.debug("LAST PAGE", viewModel.lastPage.toString())
        if (viewModel.page < viewModel.lastPage) {
          viewModel.getAllData()
          adapter.setIsLoadingMore(true)
        }
      }
    }
  }

  companion object {
    const val MEDIA_TYPE = "MEDIA_TYPE"
    fun open(mediaType: MediaType, context: Context) {
      val intent = Intent(context, SeeAllActivity::class.java)
      intent.putExtra(MEDIA_TYPE, mediaType.name)
      context.startActivity(intent)
    }
  }
}