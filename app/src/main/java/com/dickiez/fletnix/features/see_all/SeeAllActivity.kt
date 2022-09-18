package com.dickiez.fletnix.features.see_all

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dickiez.fletnix.core.base.BaseActivity
import com.dickiez.fletnix.core.constants.ContentSection
import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.databinding.ActivitySeeAllBinding
import com.dickiez.fletnix.utils.Tools
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllActivity : BaseActivity() {

  private lateinit var binding: ActivitySeeAllBinding
  private lateinit var viewModel: SeeAllViewModel

  private lateinit var mediaType: MediaType
  private lateinit var section: ContentSection
  private lateinit var adapter: SeeAllAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySeeAllBinding.inflate(layoutInflater)
    setContentView(binding.root)
    viewModel = ViewModelProvider(this)[SeeAllViewModel::class.java]
    initTypeAndSection()
    setupRecyclerView()
    viewModel.getAllData()
    viewModel.seeAllData.observe(this) {
      if (it.isNotEmpty()) {
        if (viewModel.page > 1) {
          adapter.addAll(it)
        } else {
          adapter.setData(it)
        }
      }
    }

    viewModel.isLoadingMore.observe(this) { adapter.setIsLoadingMore(it) }
  }

  private fun initTypeAndSection() {
    mediaType = MediaType.valueOf(intent.getStringExtra(MEDIA_TYPE)!!)
    section = ContentSection.valueOf(intent.getStringExtra(SECTION)!!)
    viewModel.setMediaType(mediaType)
    viewModel.setContentSection(section)
  }

  private fun setupRecyclerView() {
    adapter = SeeAllAdapter(mediaType)
    binding.recyclerViewSeeAll.adapter = adapter
    adapter.addOnItemClick { Tools.debug("ITEM CLICK", it.id.toString()) }
    adapter.addLoadMoreListener {
      if (viewModel.page < viewModel.lastPage) {
        viewModel.getAllData()
      }
    }
  }

  companion object {
    private const val MEDIA_TYPE = "MEDIA_TYPE"
    private const val SECTION = "SECTION"
    fun open(mediaType: MediaType, section: ContentSection, context: Context) {
      val intent = Intent(context, SeeAllActivity::class.java)
      intent.putExtra(MEDIA_TYPE, mediaType.name)
      intent.putExtra(SECTION, section.name)
      context.startActivity(intent)
    }
  }
}