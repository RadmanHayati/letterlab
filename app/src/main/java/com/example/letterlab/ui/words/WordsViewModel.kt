package com.example.letterlab.ui.words

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.letterlab.data.WordDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest

class WordsViewModel @ViewModelInject constructor(
    private val wordDao: WordDao
) : ViewModel() {
    val searchQuery= MutableStateFlow("")
    private val wordsFlow = searchQuery.flatMapLatest {
        wordDao.getWords(it)
    }
    val words = wordsFlow.asLiveData()
}