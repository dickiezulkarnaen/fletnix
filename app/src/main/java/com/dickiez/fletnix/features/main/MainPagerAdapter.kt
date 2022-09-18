/***
 * Author         : Dicky Zulkarnain
 * Date           : 17/09/22
 * Original File  : MainPagerAdapter
 ***/

package com.dickiez.fletnix.features.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dickiez.fletnix.features.main.content.ContentFragment
import kotlinx.coroutines.DelicateCoroutinesApi


@OptIn(DelicateCoroutinesApi::class)
class MainPagerAdapter(
  activity: FragmentActivity,
  private val pages : Array<ContentFragment>)
  : FragmentStateAdapter(activity) {

  override fun getItemCount(): Int = pages.size

  override fun createFragment(position: Int): Fragment = pages[position]

}