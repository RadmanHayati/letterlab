package com.example.letterlab.data

import androidx.room.*
import com.example.letterlab.ui.words.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    fun getWords(query: String, sortOrder: SortOrder, hideLearned: Boolean): Flow<List<Word>> =
        when (sortOrder) {
            SortOrder.BY_DATE -> getWordsSortedByDateCreated(query, hideLearned)
            SortOrder.BY_NAME -> getWordsSortedByName(query, hideLearned)
        }

    @Query("SELECT * FROM WORD_TABLE WHERE (learned != :hideLearned OR learned=0 ) AND word LIKE '%' || :searchQuery || '%' ORDER BY important DESC,word")
    fun getWordsSortedByName(searchQuery: String, hideLearned: Boolean): Flow<List<Word>>

    @Query("SELECT * FROM WORD_TABLE WHERE (learned != :hideLearned OR learned=0 ) AND word LIKE '%' || :searchQuery || '%' ORDER BY important DESC,created")
    fun getWordsSortedByDateCreated(searchQuery: String, hideLearned: Boolean): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Update
    suspend fun update(word: Word)

    @Delete
    suspend fun delete(word: Word)

}