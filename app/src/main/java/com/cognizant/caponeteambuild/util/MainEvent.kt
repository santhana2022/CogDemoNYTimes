package com.cognizant.caponeteambuild.util

import com.cognizant.caponeteambuild.data.Result

sealed class MainEvent {
    data class DataRetrievedEvent(val data: List<Result>) : MainEvent()
    data class ArticleSelectedStateEvent(val articleKey: String) : MainEvent()
    data class ErrorEvent(val message: String) : MainEvent()
}
