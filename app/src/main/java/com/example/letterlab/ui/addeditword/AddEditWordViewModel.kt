package com.example.letterlab.ui.addeditword

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letterlab.data.Word
import com.example.letterlab.data.WordDao
import com.example.letterlab.ui.ADD_WORD_RESULT_OK
import com.example.letterlab.ui.EDIT_WORD_RESULT_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

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
    var wordMeaning = state.get<String>("wordMeaning") ?: word?.meaning ?: ""
        set(value) {
            field = value
            state.set("wordMeaning", value)
        }
    private val addEditWordEventChannel = Channel<AddEditWordEvent>()
    val addEditWordEvent = addEditWordEventChannel.receiveAsFlow()
    fun onSaveClick() {
        if (wordName.isBlank()) {
            showInvalidInputMessages("Word can not be empty")
            return
        }
        if (word != null) {
            val updateWord =
                word.copy(word = wordName, meaning = wordMeaning, important = wordImportance)
            updateWord(updateWord)
        } else {
            val newWord = Word(word = wordName, meaning = wordMeaning, important = wordImportance)
            addWord(newWord)
        }
    }

    private fun addWord(word: Word) = viewModelScope.launch {
        wordDao.insert(word)
        addEditWordEventChannel.send(AddEditWordEvent.NavigateBackWithResult(ADD_WORD_RESULT_OK))
    }

    private fun updateWord(word: Word) = viewModelScope.launch {
        wordDao.update(word)
        addEditWordEventChannel.send(AddEditWordEvent.NavigateBackWithResult(EDIT_WORD_RESULT_OK))
    }

    private fun showInvalidInputMessages(msg: String) = viewModelScope.launch {
        addEditWordEventChannel.send(AddEditWordEvent.ShowInvalidInputMessage(msg))
    }

    sealed class AddEditWordEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditWordEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditWordEvent()
    }

}