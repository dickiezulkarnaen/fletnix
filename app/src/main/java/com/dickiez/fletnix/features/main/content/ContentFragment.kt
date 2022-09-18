package com.dickiez.fletnix.features.main.content

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.core.data.models.Banner
import com.dickiez.fletnix.databinding.FragmentContentBinding
import com.dickiez.fletnix.features.main.MainViewModel
import com.dickiez.fletnix.features.see_all.SeeAllActivity
import com.dickiez.fletnix.utils.Tools
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.math.abs

@DelicateCoroutinesApi
@AndroidEntryPoint
class ContentFragment private constructor() : Fragment() {

  private lateinit var mediaType: MediaType

  private lateinit var binding: FragmentContentBinding
  private var sliderJob: Job? = null

  private val images = arrayListOf<Banner>()

  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    arguments?.let { mediaType = MediaType.valueOf(it.getString(CONTENT_TYPE_KEY)!!) }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout for this fragment
    binding = FragmentContentBinding.inflate(inflater, container, false)
    when (mediaType) {
      MediaType.MOVIE -> {
        viewModel.movieBanners.observeBanner()
        viewModel.movieTrending.observeTrendingSection()
        viewModel.movieDiscover.observeDiscoverSection()
      }
      MediaType.TV_SHOW -> {
        viewModel.tvBanners.observeBanner()
        viewModel.tvTrending.observeTrendingSection()
        viewModel.tvDiscover.observeDiscoverSection()
      }
    }
    binding.textViewLabelDiscover.setOnClickListener {
      context?.let { c -> SeeAllActivity.open(mediaType, c) }
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupCarousel()
    setupTrendingSection()
    setupDiscoverSection()
  }

  private fun LiveData<ArrayList<Banner>>.observeBanner() {
    this.observe(viewLifecycleOwner) {
      if (it.isNotEmpty()) {
        images.clear()
        images.addAll(it.subList(0, 4))
        binding.viewPagerCarousel.adapter?.notifyItemInserted(0)
      }
    }
  }

  private fun LiveData<ArrayList<Banner>>.observeTrendingSection() {
    this.observe(viewLifecycleOwner) {
      if (it.isNotEmpty()) {
        (binding.recyclerViewTrending.adapter as ContentSectionAdapter).setData(it)
      }
    }
  }

  private fun LiveData<ArrayList<Banner>>.observeDiscoverSection() {
    this.observe(viewLifecycleOwner) {
      if (it.isNotEmpty()) {
        (binding.recyclerViewDiscover.adapter as ContentSectionAdapter).setData(it)
      }
    }
  }

  @SuppressLint("NotifyDataSetChanged")
  private fun setupCarousel() {
    val carouselAdapter = CarouselViewAdapter(items = images)
    binding.viewPagerCarousel.adapter = carouselAdapter
    binding.viewPagerCarousel.setPageTransformer(transformManager())
    binding.viewPagerCarousel.registerOnPageChangeCallback(viewPagerCallback)
  }

  private fun transformManager() : CompositePageTransformer {
      val compositePageTransformer = CompositePageTransformer()
      val marginPageTransformer = MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt())
      compositePageTransformer.addTransformer(marginPageTransformer)
      val pageTransformer = ViewPager2.PageTransformer { page, position ->
        val r = 1 - abs(position)
        page.scaleY = 0.85f + r * 0.15f
      }
      compositePageTransformer.addTransformer(pageTransformer)
      return compositePageTransformer
    }

  private val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
      super.onPageSelected(position)
      sliderJob?.cancel()
      sliderJob = GlobalScope.launch(Dispatchers.Main) {
        delay(2000)
        if (binding.viewPagerCarousel.currentItem == images.lastIndex) {
          binding.viewPagerCarousel.currentItem = 0
        } else {
          binding.viewPagerCarousel.currentItem = binding.viewPagerCarousel.currentItem + 1
        }
      }
    }
  }

  private fun runSliderJob() = viewPagerCallback.onPageSelected(binding.viewPagerCarousel.currentItem)

  private fun setupTrendingSection() {
    binding.recyclerViewTrending.adapter = ContentSectionAdapter()
  }

  private fun setupDiscoverSection() {
    binding.recyclerViewDiscover.adapter = ContentSectionAdapter()
  }

  private fun cancelSliderJob() {
    sliderJob?.cancel()
    sliderJob = null
  }

  override fun onPause() {
    cancelSliderJob()
    super.onPause()
  }

  override fun onStop() {
    cancelSliderJob()
    super.onStop()
  }

  override fun onResume() {
    super.onResume()
    runSliderJob()
  }

  companion object {
    private const val CONTENT_TYPE_KEY = "CONTENT_TYPE_KEY"

    @JvmStatic
    fun newInstance(contentType: MediaType) =
      ContentFragment().apply {
        arguments = Bundle().apply {
          putSerializable(CONTENT_TYPE_KEY, contentType.name)
        }
      }
  }
}