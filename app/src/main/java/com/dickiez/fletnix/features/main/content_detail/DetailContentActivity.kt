package com.dickiez.fletnix.features.main.content_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dickiez.fletnix.core.base.BaseActivity
import com.dickiez.fletnix.core.constants.BackDropSize
import com.dickiez.fletnix.core.constants.Constant
import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.databinding.ActivityDetailContentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailContentActivity : BaseActivity() {

  private lateinit var binding: ActivityDetailContentBinding

  private lateinit var viewModel: DetailContentViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProvider(this)[DetailContentViewModel::class.java]
    binding = ActivityDetailContentBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val contentId = intent.getIntExtra(CONTENT_ID, 0)
    val mediaType = MediaType.valueOf(intent.getStringExtra(MEDIA_TYPE)!!)
    viewModel.initContentAndGetData(contentId, mediaType)

    viewModel.contentDetail.observe(this) {
      Glide.with(this)
        .load("${Constant.TMDB_IMAGE_BASE_URL}${BackDropSize.W_780.value}${it.backdropPath}")
        .into(binding.imageViewBanner)
      binding.textViewVoteAverage.text = it.voteAverage?.toString() ?: "-"
      actionBar?.title = it.title ?: it.originalTitle ?: it.originalTitle ?: it.name ?: it.originalName
    }

  }

  companion object {
    private const val CONTENT_ID = "CONTENT_ID"
    private const val MEDIA_TYPE = "MEDIA_TYPE"
    fun open(contentId: Int, mediaType: MediaType, context: Context) {
      val intent = Intent(context, DetailContentActivity::class.java)
      intent.putExtra(CONTENT_ID, contentId)
      intent.putExtra(MEDIA_TYPE, mediaType.name)
      context.startActivity(intent)
    }
  }
}