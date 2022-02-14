package com.cognizant.caponeteambuild.repository.local

import androidx.room.*
import com.cognizant.caponeteambuild.data.Result

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: Result)

    suspend fun insert(resultsList: List<Result>) = resultsList.forEach { insert(it) }

    @Query("SELECT * FROM results_table WHERE section = :section")
    suspend fun fetchAllArticlesBySection(section: String): List<Result>

}