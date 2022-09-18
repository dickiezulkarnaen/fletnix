package com.dickiez.fletnix.core.lifecycle

sealed class UIState {
  object Loading : UIState()
  object NotLoading : UIState()
  object Success : UIState()
  object Error : UIState()
}
