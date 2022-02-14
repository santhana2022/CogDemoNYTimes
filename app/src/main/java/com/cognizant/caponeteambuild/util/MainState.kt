package com.cognizant.caponeteambuild.util

import com.cognizant.caponeteambuild.data.Result

sealed class MainState {
    data class Initial(val title: String) : MainState()
    data class Ready(val data: List<Result>) : MainState()
    data class Error(val message: String) : MainState()
    data class Loading(val articleApiKey: String, val forceToRefresh : Boolean) : MainState()
}
