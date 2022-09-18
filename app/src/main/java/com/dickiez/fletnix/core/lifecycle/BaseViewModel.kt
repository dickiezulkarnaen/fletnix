/***
 * Author         : Dicky Zulkarnain
 * Date           : 12/09/22
 * Original File  : BaseViewModel
 ***/

package com.dickiez.fletnix.core.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {

  private val _isLoading = MutableLiveData(false)
  val isLoading: LiveData<Boolean> get() = _isLoading

  private val _state = MutableLiveData<UIState>(UIState.Loading)
  val state: LiveData<UIState> get() = _state

  fun setIsLoading(loading: Boolean) {
    _isLoading.value = loading
  }

  fun setState(newState: UIState) {
    _state.value = newState
  }

}