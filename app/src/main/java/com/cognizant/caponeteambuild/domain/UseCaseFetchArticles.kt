package com.cognizant.caponeteambuild.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cognizant.caponeteambuild.Constant
import com.cognizant.caponeteambuild.data.Result
import com.cognizant.caponeteambuild.repositories.ArticleRepository
import com.cognizant.caponeteambuild.repository.local.NewsDao
import com.cognizant.caponeteambuild.util.MainState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UseCaseFetchArticles
@Inject constructor(
    private val articleRepository: ArticleRepository,
    private val newsDao: NewsDao,
) {

    private val TAG = "UseCaseFetchArticles"
    private val coroutineScope by lazy { CoroutineScope(Dispatchers.IO) }
    private val _resultLiveData = MutableLiveData<List<Result>>()
    val resultLiveData: LiveData<List<Result>>
        get() = _resultLiveData

    fun fetchArticles(state: MainState.Loading) {
        coroutineScope.launch {
            var result: List<Result>
            if (state.forceToRefresh) {
                Log.d(
                    TAG,
                    "Force refreshing data."
                )
                result = fetchFromRemoteAndUpdateDb(state.articleApiKey)
                _resultLiveData.postValue(result)
            } else {
                result = newsDao.fetchAllArticlesBySection(state.articleApiKey)
                if (!result.isNullOrEmpty()) {
                    if (isTimestampValid(result[0])) {
                        Log.d(
                            TAG,
                            "Using \'${state.articleApiKey}\' data from DB..."
                        )
                        _resultLiveData.postValue(result)
                    } else {
                        Log.d(
                            TAG,
                            "Timestamp on \'${state.articleApiKey}\' expired"
                        )
                        result = fetchFromRemoteAndUpdateDb(state.articleApiKey)
                        _resultLiveData.postValue(result)
                    }
                } else {
                    result = fetchFromRemoteAndUpdateDb(state.articleApiKey)
                    _resultLiveData.postValue(result)
                }
            }
        }
    }

    private fun isTimestampValid(result: Result): Boolean {
        val savedTimestamp = result.millisTimeStamp
        val maxAllowedTime = savedTimestamp.plus(Constant.Vals.TIME_MILLIS_24H)
        return maxAllowedTime > System.currentTimeMillis()
    }

    private fun saveResultToDatabase(result: List<Result>) {
        val newResult = updateDbTimestamp(result)
        coroutineScope.launch {
            newsDao.insert(newResult)
        }
    }

    private fun updateDbTimestamp(list: List<Result>): List<Result> {
        val timeStamp = System.currentTimeMillis()
        list.forEach {
            it.millisTimeStamp = timeStamp
        }
        return list
    }

    private suspend fun fetchFromRemoteAndUpdateDb(articleApiKey: String): List<Result> {
        Log.d(TAG, "Fetching \'$articleApiKey\' data from remote and saving to DB...")
        return try {
            val article = articleRepository.getArticle(articleApiKey)
            saveResultToDatabase(article.results)
            article.results
        } catch (exception: Exception) {
            emptyList()
        }
    }
}