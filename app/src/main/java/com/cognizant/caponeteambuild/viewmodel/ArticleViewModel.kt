package com.cognizant.caponeteambuild.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cognizant.caponeteambuild.domain.UseCaseFetchArticles
import com.cognizant.caponeteambuild.util.MainEvent
import com.cognizant.caponeteambuild.util.MainState
import com.cognizant.caponeteambuild.util.StateReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

/**
 *  Article ViewModel
 */
@HiltViewModel
@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class ArticleViewModel @Inject constructor(
    private val useCaseFetchArticles: UseCaseFetchArticles,
    private val reducer: StateReducer
) : ViewModel() {

    val stateLiveData: MutableLiveData<MainState> = MutableLiveData(
        MainState.Initial("")
    )

    init {
        stateLiveData.observeForever {
            handleState(it)
        }

        // Observing data coming from fetchArticles() request
        useCaseFetchArticles.resultLiveData.observeForever {
            if (it.isNullOrEmpty()) processEvent(MainEvent.ErrorEvent("Having issues retrieving data"))
            else processEvent(MainEvent.DataRetrievedEvent(it))
        }
    }

    private fun handleState(state: MainState) {
        viewModelScope.launch {
            when (state) {
                is MainState.Loading -> {
                    try {
                        useCaseFetchArticles.fetchArticles(state)
                    } catch (exception: Exception) {
                        exception.message?.let {
                            processEvent(MainEvent.ErrorEvent(it))
                        }
                    }
                }
                else -> {
                    // nothing
                }
            }
        }
    }

    fun processEvent(event: MainEvent) {
        stateLiveData.postValue(
            reducer.reduce(event, stateLiveData.value!!)
        )
    }
}