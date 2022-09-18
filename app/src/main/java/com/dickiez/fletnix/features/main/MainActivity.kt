package com.dickiez.fletnix.features.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.dickiez.fletnix.R
import com.dickiez.fletnix.core.base.BaseActivity
import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.databinding.ActivityMainBinding
import com.dickiez.fletnix.features.main.content.ContentFragment
import com.dickiez.fletnix.features.main.content_detail.DetailContentActivity
import com.dickiez.fletnix.utils.Tools
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@AndroidEntryPoint
class MainActivity : BaseActivity() {

  private lateinit var viewModel: MainViewModel
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    installSplashScreen()
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupAppBar()
    setupViewPager()
    attachViewPagerIntoTabLayout()
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_search, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    startActivity(Intent(this, DetailContentActivity::class.java))
    return super.onOptionsItemSelected(item)
  }

  private fun setupAppBar() {
    supportActionBar?.title = "TMDB"
  }

  private fun attachViewPagerIntoTabLayout() {
    TabLayoutMediator(binding.tabLayout, binding.viewPagerMain) { tab, position ->
      tab.text = viewModel.tabTitles[position]
    }.attach()
  }

  @OptIn(DelicateCoroutinesApi::class)
  private fun setupViewPager() {
    binding.viewPagerMain.isUserInputEnabled = false
    binding.viewPagerMain.adapter = MainPagerAdapter(
      this,
      arrayOf(
        ContentFragment.newInstance(MediaType.MOVIE),
        ContentFragment.newInstance(MediaType.TV_SHOW)
      )
    )
  }

}