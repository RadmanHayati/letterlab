package com.example.letterlab.ui.words

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.letterlab.data.WordDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class WordsViewModel @ViewModelInject constructor(
    private val wordDao: WordDao
) : ViewModel() {
    val searchQuery= MutableStateFlow("")
    val sortOrder= MutableStateFlow(SortOrder.BY_DATE)
    val hideLearned= MutableStateFlow(false)

    private val wordsFlow = combine(
        searchQuery,
        sortOrder,
        hideLearned
    ){ query,sortOrder,hideLearned ->
        Triple(query,sortOrder,hideLearned)
    }
        .flatMapLatest {(query,sortOrder,hideLearned) ->
        wordDao.getWords(query,sortOrder,hideLearned)
    }
    val words = wordsFlow.asLiveData()
}
enum class SortOrder{BY_NAME,BY_DATE}