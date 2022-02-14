package com.cognizant.caponeteambuild.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.Test


class StateReducerTest {

    val reducer = StateReducer()

    @Test
    fun `state should be ready when Loading state and DataRetrievedEvent passed to the reduce method`() {
        val result =
            reducer.reduce(
                MainEvent.DataRetrievedEvent(emptyList()),
                MainState.Loading("key", true)
            )
        assertTrue(result is MainState.Ready)

    }

    @Test
    fun `state should be Loading when Initial state and ArticleSelectedStateEvent passed to the reduce method`() {
        val result = reducer.reduce(
            MainEvent.ArticleSelectedStateEvent("Article key"),
            MainState.Initial("")
        )
        assertTrue(result is MainState.Loading)
    }

    @Test
    fun `only ArticleSelectedStateEvent should be passed to the reduce method when an article is selected`() {
        val result = reducer.reduce(MainEvent.ErrorEvent(""), MainState.Loading("key", true))
        assertTrue(result is MainState.Error)
    }

    @Test
    fun `when state is Initial event must be ArticleSelectedStateEvent`() {
        val result = reducer.reduce(MainEvent.DataRetrievedEvent(listOf()), MainState.Initial(""))
        assertEquals(MainState.Error("unexpected event"), result)
    }

    @Test
    fun `when state is Loading event must be DataRetrievedEvent`() {
        val result = reducer.reduce(MainEvent.ErrorEvent(""), MainState.Loading("", true))
        assertTrue(result is MainState.Error)
    }

    @Test
    fun `when state is Loading if event is ArticleSelectdStateEvent`() {
        val result =
            reducer.reduce(MainEvent.ArticleSelectedStateEvent(""), MainState.Loading("", true))
        assertTrue(result is MainState.Error)
    }

}