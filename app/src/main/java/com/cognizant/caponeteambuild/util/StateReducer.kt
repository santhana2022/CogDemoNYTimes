package com.cognizant.caponeteambuild.util

import javax.inject.Inject

class StateReducer @Inject constructor() {
    fun reduce(event: MainEvent, state: MainState): MainState {
        return when (state) {
            is MainState.Initial -> reduceInitialState(event, state)
            is MainState.Loading -> reduceLoadingState(event, state)
            is MainState.Ready -> reduceReadyState(event, state)
            is MainState.Error -> reduceErrorState(event, state)
        }
    }

    private fun reduceErrorState(event: MainEvent, state: MainState.Error): MainState {
        return MainState.Error(state.message)
    }

    private fun reduceReadyState(event: MainEvent, state: MainState.Ready): MainState {
        return when (event) {
            is MainEvent.ErrorEvent -> MainState.Error(event.message)
            else -> MainState.Error("unexpected event")
        }
    }

    private fun reduceLoadingState(event: MainEvent, state: MainState.Loading): MainState {
        return when (event) {
            is MainEvent.DataRetrievedEvent -> MainState.Ready(event.data)
            is MainEvent.ErrorEvent -> MainState.Error(event.message)
            else -> MainState.Error("unexpected event")
        }
    }

    private fun reduceInitialState(event: MainEvent, state: MainState.Initial): MainState {
        return when (event) {
            is MainEvent.ArticleSelectedStateEvent -> MainState.Loading(event.articleKey,false)
            is MainEvent.ErrorEvent -> MainState.Error(event.message)
            else -> MainState.Error("unexpected event")
        }
    }
}