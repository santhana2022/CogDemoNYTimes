package com.cognizant.caponeteambuild.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cognizant.caponeteambuild.domain.UseCaseFetchArticles
import com.cognizant.caponeteambuild.repositories.ArticleRepository
import com.cognizant.caponeteambuild.repository.local.NewsDao
import com.cognizant.caponeteambuild.util.MainEvent
import com.cognizant.caponeteambuild.util.MainState
import com.cognizant.caponeteambuild.util.StateReducer
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ArticleViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var useCaseFetchArticles: UseCaseFetchArticles

    @MockK
    lateinit var articleRepository: ArticleRepository

    @MockK
    lateinit var newsDao: NewsDao

    @MockK
    lateinit var stateReducer: StateReducer

    lateinit var articleViewModel: ArticleViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCaseFetchArticles = UseCaseFetchArticles(articleRepository, newsDao)
        articleViewModel = ArticleViewModel(useCaseFetchArticles, stateReducer)
    }

    @After
    fun tearDown() {

    }


    @Test
    @ExperimentalCoroutinesApi
    fun processEvent() {
        every { stateReducer.reduce(any(), any()) } returns MainState.Initial("")
        articleViewModel.processEvent(MainEvent.ArticleSelectedStateEvent("dummyKey"))
        verify {
            stateReducer.reduce(MainEvent.ArticleSelectedStateEvent("dummyKey"), any())
        }
    }
}