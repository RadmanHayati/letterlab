package com.example.letterlab.ui.words

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.letterlab.data.WordDao

class WordsViewModel @ViewModelInject constructor(
    private val wordDao: WordDao
) : ViewModel() {
    val words = wordDao.getWords().asLiveData()
}