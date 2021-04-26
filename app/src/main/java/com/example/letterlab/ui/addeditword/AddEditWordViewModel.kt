package com.example.letterlab.ui.addeditword

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.letterlab.data.Word
import com.example.letterlab.data.WordDao

class AddEditWordViewModel @ViewModelInject constructor(
    private val wordDao: WordDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {
    val word = state.get<Word>("word")
    var wordName = state.get<String>("wordName") ?: word?.word ?: ""
        set(value) {
            field = value
            state.set("wordName", value)
        }
    var wordImportance = state.get<Boolean>("wordImportance") ?: word?.important ?: false
        set(value) {
            field = value
            state.set("wordImportance", value)
        }

}