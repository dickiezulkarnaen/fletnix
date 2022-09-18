/***
 * Author         : Dicky Zulkarnain
 * Date           : 18/09/22
 * Original File  : SeeAllViewModel
 ***/

package com.dickiez.fletnix.features.see_all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dickiez.fletnix.core.data.models.ContentResult
import com.dickiez.fletnix.core.data.network.ApiState
import com.dickiez.fletnix.core.lifecycle.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(private val repository: SeeAllRepository) : BaseViewModel() {

  private val _seeAllData = MutableLiveData<ArrayList<ContentResult>>()
  val seeAllData :LiveData<ArrayList<ContentResult>> = _seeAllData

  private var _page = 0
  val page : Int get() = _page
  private var _lastPage = 0
  val lastPage : Int get() = _lastPage
  private val perPage = 10

  fun getAllData() = viewModelScope.launch {
    _page++
    repository.getAllData(_page) {
      if (it.state == ApiState.Finish) {
        if (it.response?.isSuccessful == true) {
          _seeAllData.value = applyData(it.response.body?.results)
          _page = it.response.body?.page ?: _page
          _lastPage = it.response.body?.totalPages ?: _lastPage
        }
      }
    }
  }

  private fun applyData(results: List<ContentResult?>?) : ArrayList<ContentResult> {
    val data = arrayListOf<ContentResult>()
    results?.forEach { result ->
      result?.let { data.add(it) }
    }
    return data
  }

}